package avanwieringen.sudoku.solver;

import avanwieringen.sudoku.Sudoku;

public class SolverResult {

	public enum Type {
		SOLVED,
		SOLVABLE,
		UNSOLVABLE;
	}
	
	/**
	 * Specifies the result type of this Result
	 */
	private Type result;
	
	/**
	 * The Sudoku that is concerned
	 */
	private Sudoku sudoku;
	
	/**
	 * Constructs the solver result
	 * @param s
	 * @param result
	 */
	public SolverResult(Sudoku s, Type result) {
		this.result = result;
		this.sudoku = s;
	}
	
	/**
	 * Returns the Sudoku belonging to this result
	 * @return Sudoku
	 */
	public Sudoku getSoduku() {
		return this.sudoku;
	}
	
	/**
	 * Returns the Result Type belonging to this result
	 * @return Result Type
	 */
	public Type getResult() {
		return this.result;
	}
	
}
