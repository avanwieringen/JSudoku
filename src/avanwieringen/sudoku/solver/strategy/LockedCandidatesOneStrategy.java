package avanwieringen.sudoku.solver.strategy;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import com.rits.cloning.Cloner;

import avanwieringen.sudoku.Cell;
import avanwieringen.sudoku.House;
import avanwieringen.sudoku.Sudoku;

public class LockedCandidatesOneStrategy implements StrategyInterface {

	Cloner cloner = new Cloner();
	
	/**
	 * Is the Sudoku changed by removing candidates?
	 */
	private boolean candidatesRemoved;
	
	/**
	 * Finds locked candidates by traversing blocks and rows/cols
	 */
	public Sudoku[] solve(Sudoku s) {
		this.candidatesRemoved = false;
		Sudoku n = cloner.deepClone(s);
		
		HashMap<Integer, Vector<House>> candidatesCol = new HashMap<Integer, Vector<House>>();
		HashMap<Integer, Vector<House>> candidatesRow = new HashMap<Integer, Vector<House>>();
		for(House h : n.getBoxes()) {
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

}
