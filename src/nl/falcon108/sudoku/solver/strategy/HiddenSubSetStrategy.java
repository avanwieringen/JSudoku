package nl.falcon108.sudoku.solver.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.ArrayUtils;

import com.rits.cloning.Cloner;

import nl.falcon108.sudoku.Cell;
import nl.falcon108.sudoku.House;
import nl.falcon108.sudoku.Sudoku;
import nl.falcon108.tools.Collections;

public class HiddenSubSetStrategy extends AbstractStrategy {

	public enum Type {
		PAIR(2),
		TRIPLE(3),
		QUAD(4);
		
		private int size;

		Type(int size) {
			this.size = size;
		}
		
		public int getSize() {
			return this.size;
		}
	}
	
	Type type;
	
	/**
	 * Is the Sudoku changed by removing candidates?
	 */
	private boolean candidatesRemoved;
	
	Cloner cloner = new Cloner();
	
	/**
	 * Which HiddenSubSetStrategy type?
	 * @param Type
	 */
	public HiddenSubSetStrategy(Type t) {
		this.type = t;
	}
	
	public Sudoku[] solve(Sudoku s) {
		this.candidatesRemoved = false;
		Sudoku n = cloner.deepClone(s);
		
		//this.traverseHouses(n.getBoxes());
		this.traverseHouses(ArrayUtils.subarray(n.getRows(), 3, 4));
		//this.traverseHouses(ArrayUtils.subarray(n.getRows(), 1, 2));
		//this.traverseHouses(n.getColumns());
		
		if(candidatesRemoved) {
			return new Sudoku[] { n };
		} else {
			return new Sudoku[0];
		}
	}
	
	private void traverseHouses(House[] hs) {
		ArrayList<Cell> sets;
		ArrayList<Integer> numbers;
		ArrayList<ArrayList<Integer>> combinations;
		ArrayList<Integer> temp;
		HashMap<ArrayList<Integer>, ArrayList<Cell>> mapping;
		for(House h : hs) {
			sets = new ArrayList<Cell>();
			
			// get all candidates that fit the requirements
			for(Cell c : h.getCells()) {
				if(!c.isFilled() && c.getCandidates().length >= 2) {
					sets.add(c);
				}
			}
			
			// get all numbers in the cells
			numbers = new ArrayList<Integer>();
			for(Cell c : sets) {
				for(int i : c.getCandidates()) {
					if(!numbers.contains(i)) {
						numbers.add(i);
					}
				}
			}
			
			// get all combinations of length Type.size and look which cells have candidates in those combinations
			combinations = Collections.combinations(numbers, this.type.getSize());
			mapping = new  HashMap<ArrayList<Integer>, ArrayList<Cell>>();
			for(Cell c : sets) {
				for(ArrayList<Integer> comb : combinations) {
					for(int i_c : c.getCandidates()) {
						if(comb.contains(i_c)) {
							if(!mapping.containsKey(comb)) {
								mapping.put(comb, new ArrayList<Cell>());
							}
							
							if(!mapping.get(comb).contains(c)) {
								mapping.get(comb).add(c);
							}
							break;
						}
					}
				}
			}
			
			// traverse mapping and check if combination is fully covered
			for(Entry<ArrayList<Integer>, ArrayList<Cell>> entry : mapping.entrySet()) {
				if(entry.getValue().size() == this.type.getSize()) {
					
					// check if its covered
					temp = new ArrayList<Integer>();
					for(Cell c : entry.getValue()) {
						for(int i_c : c.getCandidates()) {
							if(entry.getKey().contains(i_c) && !temp.contains(i_c)) {
								temp.add(i_c);
							}
						}
					}
					
					// it is covered and we can remove the other candidates from the cells
					if(temp.size() == this.type.getSize()) {
						for(Cell c : entry.getValue()) {
							for(int i_c : c.getCandidates()) {
								if(!entry.getKey().contains(i_c)) {
									c.removePossibility(i_c);
									this.candidatesRemoved = true;
								}
							}
						}
					}
				}
			}
		}
	}
	
	public String getName() {
		String name = this.type.name().toLowerCase();
		name = (String.valueOf(name.charAt(0)).toUpperCase()).concat(name.substring(1)).concat("s");
		return super.getName().replaceAll("SubSet", name);
	}

}
