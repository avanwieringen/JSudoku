package nl.falcon108.sudoku.solver;

import java.util.Vector;

import nl.falcon108.sudoku.Sudoku;
import nl.falcon108.sudoku.solver.strategy.AbstractStrategy;


public class Solver {

	/**
	 * Contains all the strategies this solver uses in that order
	 */
	private Vector<AbstractStrategy> strategies = new Vector<AbstractStrategy>();
	
	/**
	 * Generates a solver with the specified array of Strategies
	 * @param strategies
	 */
	public Solver(AbstractStrategy[] strategies) {
		for (AbstractStrategy s : strategies) {
			this.addStrategy(s);
		}
	}
	
	/**
	 * Adds a strategy to the solver
	 * @param strategy
	 */
	public void addStrategy(AbstractStrategy strategy) {
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
		boolean strategiesLeft = true;
		boolean sudokuChanged;
		while(!s.isSolved() && strategiesLeft) {
			sudokuChanged = false;
			for (AbstractStrategy strategy : this.strategies) {
				//System.out.println("[Level " + String.valueOf(level) + "] Using solver " + strategy.getName());
				solutions = strategy.solve(s);
				
				if(solutions.length == 1) {
					s = solutions[0];
					sudokuChanged = true;
					break;
				} else if(solutions.length > 1) {
					for(Sudoku solution : solutions) {
						childResult = this.iterate(solution, ++level);
						if(childResult.getResult().equals(SolverResult.Type.SOLVED)) {
							return childResult;
						}
					}
				}
			}
			
			if(!sudokuChanged) { strategiesLeft = false; }
		}
		
		if(s.isSolved()) {
			return new SolverResult(s, SolverResult.Type.SOLVED);
		} else {
			return new SolverResult(s, SolverResult.Type.UNSOLVABLE);
		}
	}
}
