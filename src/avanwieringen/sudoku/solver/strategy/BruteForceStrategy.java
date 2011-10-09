package avanwieringen.sudoku.solver.strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.sun.tools.javac.util.Pair;

import avanwieringen.sudoku.Cell;
import avanwieringen.sudoku.Sudoku;

public class BruteForceStrategy implements StrategyInterface {

	/**
	 * Determines which cells have the least amount of choices and creates possible solutions using these steps
	 */
	public Sudoku[] solve(Sudoku s) {
		int currentMin = (int) Math.sqrt(s.getCellCount()) + 1;
		Vector<int[]> possibleSteps = new Vector<int[]>();
		
		int[] possibilities;
		Cell currentCell;
		for(int r = 0; r < s.getRowCount(); r++) {
			for(int c = 0; c < s.getColumnCount(); c++) {
				currentCell = s.getCell(r, c);
				if(!currentCell.isValid()) { return new Sudoku[0]; }
				if(!currentCell.isFilled()) {
					possibilities = currentCell.getPossibilities();
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
		
		System.out.println(possibleSteps.size() + ":");
		if(possibleSteps.size() > 0) {
			Sudoku[] solutions = new Sudoku[possibleSteps.size()];
			int i = 0;
			for(int[] poss : possibleSteps) {
				try {
					solutions[i] = (Sudoku) s.clone();
					System.out.println("Current cell value: " + solutions[i].getValue(poss[0], poss[1]));
					System.out.println(i + ": " + poss[0] + "," + poss[1] + ":" + poss[2]);
					solutions[i].setValue(poss[0], poss[1], poss[2]);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				i++;
			}
			return solutions;
		} else {
			return new Sudoku[0];
		}		
	}

}
