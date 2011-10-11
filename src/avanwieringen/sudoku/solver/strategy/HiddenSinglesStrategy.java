package avanwieringen.sudoku.solver.strategy;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import com.rits.cloning.Cloner;

import avanwieringen.sudoku.Cell;
import avanwieringen.sudoku.House;
import avanwieringen.sudoku.Sudoku;

public class HiddenSinglesStrategy implements StrategyInterface {

	Cloner cloner = new Cloner();
	
	/** 
	 * Finds the hidden singles of the Sudoku
	 */
	public Sudoku[] solve(Sudoku s) {
		
		Sudoku n = cloner.deepClone(s);
		for(int i = 0; i < n.getBoxCount(); i++) {
			try {
				findHiddensInHouse(n, n.getRow(i));
				findHiddensInHouse(n, n.getColumn(i));
				findHiddensInHouse(n, n.getBox(i));
			} catch(IndexOutOfBoundsException e) {
				return new Sudoku[0];
			}
		}		
		
		// is there a change?
		if(n.getFilledCells() != s.getFilledCells()) {
			return new Sudoku[] { n };
		} else {
			return new Sudoku[0];
		}
	}
	
	private void findHiddensInHouse(Sudoku s, House h) {
		
		// sort the candidates
		HashMap<Integer, Vector<Cell>> candidates = new HashMap<Integer, Vector<Cell>>();
		for(Cell c : h.getCells()) {
			if(!c.isFilled()) {
				for(int n : c.getCandidates()) {
					if(!candidates.containsKey(n)) {
						candidates.put(n, new Vector<Cell>());
					}
					candidates.get(n).add(c);
				}
			}
		}
		
		// find the hidden singles
		for(Entry<Integer, Vector<Cell>> entry : candidates.entrySet()) {
			if(entry.getValue().size() == 1) {
				entry.getValue().get(0).setValue(entry.getKey());
			}
		}
	}

}
