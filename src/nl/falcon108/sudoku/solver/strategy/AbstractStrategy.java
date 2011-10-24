package nl.falcon108.sudoku.solver.strategy;

import nl.falcon108.sudoku.Sudoku;

public abstract class AbstractStrategy {

	public abstract Sudoku[] solve(Sudoku s);
	
	public String getName() {
		return this.getClass().getSimpleName();
	}
	
}
