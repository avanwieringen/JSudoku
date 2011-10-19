package avanwieringen.sudoku.solver.strategy;

import avanwieringen.sudoku.Sudoku;

public abstract class AbstractStrategy {

	public abstract Sudoku[] solve(Sudoku s);
	
	public String getName() {
		return this.getClass().getSimpleName();
	}
	
}
