package nl.falcon108.sudoku.solver.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import nl.falcon108.sudoku.Cell;
import nl.falcon108.sudoku.House;
import nl.falcon108.sudoku.Sudoku;
import nl.falcon108.tools.Collections;


import com.rits.cloning.Cloner;


public class NakedSubSetStrategy extends AbstractStrategy {

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
	
	Cloner cloner = new Cloner();
	
	/**
	 * Is the Sudoku changed by removing candidates?
	 */
	private boolean candidatesRemoved;
	
	/**
	 * Which NakedSubSetStrategy type?
	 * @param Type
	 */
	public NakedSubSetStrategy(Type t) {
		this.type = t;
	}
	
	@Override
	public Sudoku[] solve(Sudoku s) {
		this.candidatesRemoved = false;
		Sudoku n = cloner.deepClone(s);
		
		this.traverseHouses(n.getBoxes());
		this.traverseHouses(n.getRows());
		this.traverseHouses(n.getColumns());
		
		if(candidatesRemoved) {
			return new Sudoku[] { n };
		} else {
			return new Sudoku[0];
		}		
	}
	
	private void traverseHouses(House[] hs) {
		ArrayList<Cell> sets;
		ArrayList<Integer> numbers;
		ArrayList<Integer> temp;
		ArrayList<ArrayList<Integer>> combinations;
		HashMap<ArrayList<Integer>, ArrayList<Cell>> mapping;
		for(House h : hs) {
			sets = new ArrayList<Cell>();
			
			// get all candidates that fit the requirements
			for(Cell c : h.getCells()) {
				if(!c.isFilled() && c.getCandidates().length >= 2 && c.getCandidates().length <= this.type.getSize()) {
					sets.add(c);
				}
			}			
			
			// get all combinations of type length
			// first get all unique values
			numbers = new ArrayList<Integer>();
			for(Cell c : sets) {
				for(int i : c.getCandidates()) {
					if(!numbers.contains(i)) {
						numbers.add(i);
					}
				}
			}
			
			// no create all the combinations and traverse them with the sets
			mapping = new  HashMap<ArrayList<Integer>, ArrayList<Cell>>();
			combinations = Collections.combinations(numbers, this.type.getSize());
			for(Cell c : sets) {
				for(ArrayList<Integer> comb : combinations) {
					if(Collections.fits(c.getCandidates(), comb)) {
						if(!mapping.containsKey(comb)) {
							mapping.put(comb, new ArrayList<Cell>());
						}
						mapping.get(comb).add(c);
					}
				}
			}
			
			// traverse mapping and check if combination is fully covered
			for(Entry<ArrayList<Integer>, ArrayList<Cell>> entry : mapping.entrySet()) {
				if(entry.getValue().size() == this.type.getSize()) {
					temp = new ArrayList<Integer>();
					for(Cell c : entry.getValue()) {
						for(int i : c.getCandidates()) {
							if(!temp.contains(i)) { temp.add(i); }
						}
					}
					
					if(temp.size() == entry.getKey().size()) {
						for(Cell ch : h.getCells()) {
							for(Integer ci : entry.getKey()) {
								if(!ch.isFilled() && ch.hasCandidate(ci) && !entry.getValue().contains(ch)) {
									ch.removePossibility(ci);
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
