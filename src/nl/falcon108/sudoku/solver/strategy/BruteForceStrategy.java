package nl.falcon108.sudoku.solver.strategy;

import java.util.Vector;

import nl.falcon108.sudoku.Cell;
import nl.falcon108.sudoku.Sudoku;

import com.rits.cloning.Cloner;


public class BruteForceStrategy extends AbstractStrategy {

	Cloner cloner = new Cloner();
	/**
	 * Determines which cells have the least amount of choices and creates possible solutions using these steps
	 */
	public Sudoku[] solve(Sudoku s) {
		int currentMin = (int) Math.sqrt(s.getCellCount()) + 1;
		Vector<int[]> possibleSteps = new Vector<int[]>();
		
		// gets the cell with the least amount of possibilities
		int[] possibilities;
		Cell currentCell;
		for(int r = 0; r < s.getRowCount(); r++) {
			for(int c = 0; c < s.getColumnCount(); c++) {
				currentCell = s.getCell(r, c);
				if(!currentCell.isValid()) { return new Sudoku[0]; }
				if(!currentCell.isFilled()) {
					possibilities = currentCell.getCandidates();
					if(possibilities.length < currentMin && possibilities.length > 1) {
						currentMin = possibilities.length;
						possibleSteps.clear();
						for(int p : possibilities) {
							possibleSteps.add(new int[] { r, c, p} );
						}
					}
				}
			}
		}
		
		// perform those steps
		if(possibleSteps.size() > 0) {
			Sudoku[] solutions = new Sudoku[possibleSteps.size()];
			int i = 0;
			for(int[] poss : possibleSteps) {
					solutions[i] = cloner.deepClone(s);
					solutions[i].setValue(poss[0], poss[1], poss[2]);
					i++;
			}
			return solutions;
		} else {
			return new Sudoku[0];
		}		
	}

}
