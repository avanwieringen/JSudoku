package avanwieringen.sudoku.solver;

import avanwieringen.sudoku.Sudoku;

public class SolverResult {

	public enum Type {
		SOLVED,
		SOLVABLE,
		UNSOLVABLE;
	}
	
	private Type result;
	
	private Sudoku sudoku;
	
	public SolverResult(Sudoku s, Type result) {
		this.result = result;
		this.sudoku = s;
	}
	
	public Sudoku getSoduku() {
		return this.sudoku;
	}
	
	public Type getResult() {
		return this.result;
	}
	
}
