package nl.falcon108.sudoku.solver.strategy;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import nl.falcon108.sudoku.Cell;
import nl.falcon108.sudoku.House;
import nl.falcon108.sudoku.Sudoku;

import org.apache.commons.lang3.ArrayUtils;


import com.rits.cloning.Cloner;

public class XWingStrategy extends AbstractStrategy {

	Cloner cloner = new Cloner();

	/**
	 * Is the Sudoku changed by removing candidates?
	 */
	private boolean candidatesRemoved;
	
	/**
	 * The current house that is traversed
	 * @author arjan
	 *
	 */
	private enum Type {
		ROW,
		COLUMN
	}
	
	/**
	 * Finds locked candidates by following the X-Wing strategy
	 */
	public Sudoku[] solve(Sudoku s) {
		this.candidatesRemoved = false;
		Sudoku n = cloner.deepClone(s);
		
		for(int d = 1; d <= n.getBoxCount(); d++) {
			traverseHouses(n.getRows(), d, Type.ROW);
			traverseHouses(n.getColumns(), d, Type.COLUMN);
		}
			
		if(candidatesRemoved) {
			return new Sudoku[] { n };
		} else {
			return new Sudoku[0];
		}
	}
	
	private void traverseHouses(House[] houses, int d, Type type) {
		HashMap<Vector<House>, Vector<House>> map = new HashMap<Vector<House>, Vector<House>>();
		Vector<House> tranp = new Vector<House>();
				
		for(House h : houses) {
			tranp = new Vector<House>();
			for(Cell c : h.getCells()) {
				if(!c.isFilled()) {
					if(c.hasCandidate(d)) {
						tranp.add(type.equals(Type.ROW) ? c.getColumn() : c.getRow());
					}
				}
			}
			
			if(!map.containsKey(tranp)) { map.put(tranp, new Vector<House>()) ;}
			map.get(tranp).add(h);
		}
		
		for(Entry<Vector<House>, Vector<House>> entry : map.entrySet()) {
			if(entry.getKey().size() == 2 && entry.getValue().size() == 2) {
				for(House h : entry.getKey()) {
					for(Cell c : h.getCells()) {
						if(!c.isFilled() && c.hasCandidate(d) && !entry.getValue().contains(type.equals(Type.ROW) ? c.getRow() : c.getColumn())) {
							c.removePossibility(d);		
							this.candidatesRemoved = true;
						}
					}
				}
			}
		}
	}
	
}
