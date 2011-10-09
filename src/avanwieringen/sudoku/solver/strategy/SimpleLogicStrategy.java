package avanwieringen.sudoku.solver.strategy;

import java.util.HashMap;
import java.util.Map;

import com.sun.tools.javac.util.Pair;

import avanwieringen.sudoku.Cell;
import avanwieringen.sudoku.Sudoku;

public class SimpleLogicStrategy implements StrategyInterface {

	public Sudoku[] solve(Sudoku s) {
		//if(!s.possibilitiesReduced()) {
		//	s.reducePossibilities();
		//}
		
		HashMap<Pair<Integer, Integer>, Integer> steps = new HashMap<Pair<Integer,Integer>, Integer>();
		Cell currentCell;
		for(int r = 0; r < s.getRowCount(); r++) {
			for(int c = 0; c < s.getColumnCount(); c++) {
				currentCell = s.getCell(r, c);
				if(!currentCell.isValid()) { return new Sudoku[0]; }
				if(!currentCell.isFilled()) {
					if(currentCell.getPossibilities().length==1) {;
						steps.put(new Pair<Integer, Integer>(r, c), currentCell.getPossibilities()[0]);
					}
				}
			}
		}
		
		if(steps.size() > 0) {
			Sudoku[] solution = new Sudoku[1];
			try {
				solution[0] = (Sudoku) s.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			for(Map.Entry<Pair<Integer, Integer>, Integer> entry : steps.entrySet()) {
				//System.out.println("Setting " + entry.getKey().fst + "," + entry.getKey().snd + ":" + entry.getValue());
				solution[0].setValue(entry.getKey().fst, entry.getKey().snd, entry.getValue());
			}
			return solution;
		} else {
			return new Sudoku[0];
		}
	}
}
