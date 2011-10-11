package avanwieringen.sudoku.solver.strategy;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import com.rits.cloning.Cloner;

import avanwieringen.sudoku.Cell;
import avanwieringen.sudoku.House;
import avanwieringen.sudoku.Sudoku;

public class LockedCandidatesTwoStrategy implements StrategyInterface {

	Cloner cloner = new Cloner();
	
	/**
	 * Is the Sudoku changed by removing candidates?
	 */
	private boolean candidatesRemoved;

	/**
	 * Finds locked candidates by traversing rows/cols and blocks
	 */
	public Sudoku[] solve(Sudoku s) {
		this.candidatesRemoved = false;
		Sudoku n = cloner.deepClone(s);
		
		for(House r : n.getRows()) {
			traverseHouse(n, r);
		}
		for(House c : n.getColumns()) {
			traverseHouse(n, c);
		}
		
		if(candidatesRemoved) {
			return new Sudoku[] { n };
		} else {
			return new Sudoku[0];
		}
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