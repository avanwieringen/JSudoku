package nl.falcon108.sudoku.solver.strategy;

import java.util.HashMap;
import java.util.Vector;
import java.util.Map.Entry;

import nl.falcon108.sudoku.Cell;
import nl.falcon108.sudoku.House;
import nl.falcon108.sudoku.Sudoku;


import com.rits.cloning.Cloner;

public class LockedCandidatesStrategy extends AbstractStrategy {

	Cloner cloner = new Cloner();
	
	/**
	 * Is the Sudoku changed by removing candidates?
	 */
	private boolean candidatesRemoved;
	
	/**
	 * Finds locked candidates by traversing boxes and rows/cols
	 */
	public Sudoku[] solve(Sudoku s) {
		this.candidatesRemoved = false;
		Sudoku n = cloner.deepClone(s);
		
		for(int i = 0; i < n.getBoxCount(); i++) {
			traverseBox(n, n.getBox(i));
			traverseHouse(n, n.getRow(i));
			traverseHouse(n, n.getColumn(i));
		}	
			
		if(candidatesRemoved) {
			return new Sudoku[] { n };
		} else {
			return new Sudoku[0];
		}
	}
	
	private void reduceCandidates(HashMap<Integer, Vector<House>> map, House ref) {
		for(Entry<Integer, Vector<House>> entry : map.entrySet()) {
			if(entry.getValue().size() == 1) {
				for(Cell c : entry.getValue().get(0).getCells()) {
					if(!c.isFilled()) {
						
						if(! c.getBox().equals(ref) && c.hasCandidate(entry.getKey()) ) {
							this.candidatesRemoved = true;
							c.removePossibility(entry.getKey());
						}
					}
				}
			}
		}
	}
	
	private void traverseBox(Sudoku n, House h) {
		HashMap<Integer, Vector<House>> candidatesCol = new HashMap<Integer, Vector<House>>();
		HashMap<Integer, Vector<House>> candidatesRow = new HashMap<Integer, Vector<House>>();
		candidatesCol.clear();
		candidatesRow.clear();
		for(Cell c : h.getCells()) {
			if(!c.isFilled()) {
				for(int p : c.getCandidates()) {
					if(!candidatesCol.containsKey(p)) candidatesCol.put(p, new Vector<House>());
					if(!candidatesRow.containsKey(p)) candidatesRow.put(p, new Vector<House>());
					
					if(!candidatesCol.get(p).contains(c.getColumn())) candidatesCol.get(p).add(c.getColumn());
					if(!candidatesRow.get(p).contains(c.getRow()))    candidatesRow.get(p).add(c.getRow());
				}
			}
		}
		
		reduceCandidates(candidatesCol, h);
		reduceCandidates(candidatesRow, h);
	}
	
	private void traverseHouse(Sudoku n, House h) {
		HashMap<Integer, Vector<House>> candidates = new HashMap<Integer, Vector<House>>();
		for(Cell c : h.getCells()) {
			if(!c.isFilled()) {
				for(int p : c.getCandidates()) {
					if(!candidates.containsKey(p)) candidates.put(p, new Vector<House>());					
					if(!candidates.get(p).contains(c.getBox())) candidates.get(p).add(c.getBox());
				}
			}
		}
		
		for(Entry<Integer, Vector<House>> entry : candidates.entrySet()) {
			if(entry.getValue().size() == 1) {
				for(Cell c : entry.getValue().get(0).getCells()) {
					if(!c.isFilled()) {						
						if(!c.getColumn().equals(h) && !c.getRow().equals(h) && c.hasCandidate(entry.getKey()) ) {
							this.candidatesRemoved = true;
							c.removePossibility(entry.getKey());
						}
					}
				}
			}
		}
	}
}
