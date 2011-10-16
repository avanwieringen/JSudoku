package avanwieringen.sudoku.solver;

import java.util.Vector;

import avanwieringen.sudoku.Sudoku;
import avanwieringen.sudoku.renderer.StringRenderer;
import avanwieringen.sudoku.solver.strategy.StrategyInterface;

public class Solver {

	/**
	 * Contains all the strategies this solver uses in that order
	 */
	private Vector<StrategyInterface> strategies = new Vector<StrategyInterface>();
	
	/**
	 * Generates a solver with the specified array of Strategies
	 * @param strategies
	 */
	public Solver(StrategyInterface[] strategies) {
		for (StrategyInterface s : strategies) {
			this.addStrategy(s);
		}
	}
	
	/**
	 * Adds a strategy to the solver
	 * @param strategy
	 */
	public void addStrategy(StrategyInterface strategy) {
		this.strategies.add(strategy);
	}
	
	/**
	 * Initiates the solving routine
	 * @param Sudoku
	 * @return The result of the solver routine
	 */
	public SolverResult solve(Sudoku s) {		
		if(!s.isValid()) return new SolverResult(s, SolverResult.Type.UNSOLVABLE);
		if(s.isSolved()) return new SolverResult(s, SolverResult.Type.SOLVED);
		
		return this.iterate(s, 0);
	}
	
	/**
	 * Iteration loop that keeps looping until the Sudoku is solved or failed
	 * @param s The current Sudoku in the iteration
	 * @param level The current level of iteration
	 * @return The result of the iteration
	 */
	private SolverResult iterate(Sudoku s, int level) {
		Sudoku[] solutions;
		SolverResult childResult;
		for (StrategyInterface strategy : this.strategies) {
			System.out.println("[Level " + String.valueOf(level) + "] Using solver " + strategy.getClass().getSimpleName());
			
			solutions = strategy.solve(s);
			if(solutions.length > 0) {
				for (Sudoku solution : solutions) {
					if(solution.isSolved()) return new SolverResult(solution, SolverResult.Type.SOLVED);
					if(solution.isValid()) {
						childResult = this.iterate(solution, level++);
						if(childResult.getResult().equals(SolverResult.Type.SOLVED)) {
							return childResult;
						}
					}
				}
			}
		}
		return new SolverResult(s, SolverResult.Type.UNSOLVABLE);
	}
}
