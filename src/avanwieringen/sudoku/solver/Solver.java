package avanwieringen.sudoku.solver;

import java.util.Vector;

import avanwieringen.sudoku.Sudoku;
import avanwieringen.sudoku.renderer.StringRenderer;
import avanwieringen.sudoku.solver.strategy.StrategyInterface;

public class Solver {

	private Vector<StrategyInterface> strategies = new Vector<StrategyInterface>();
	
	public Solver(StrategyInterface[] strategies) {
		for (StrategyInterface s : strategies) {
			this.addStrategy(s);
		}
	}
	
	public void addStrategy(StrategyInterface strategy) {
		this.strategies.add(strategy);
	}
	
	public SolverResult solve(Sudoku s) {		
		if(!s.isValid()) return new SolverResult(s, SolverResult.Type.UNSOLVABLE);
		if(s.isSolved()) return new SolverResult(s, SolverResult.Type.SOLVED);
		
		return this.iterate(s, 0);
	}
	
	private SolverResult iterate(Sudoku s, int level) {
		Sudoku[] solutions;
		SolverResult childResult;
		for (StrategyInterface strategy : this.strategies) {
			solutions = strategy.solve(s);
			if(solutions.length > 0) {
				for (Sudoku solution : solutions) {
					if(solution.isSolved()) return new SolverResult(solution, SolverResult.Type.SOLVED);
					if(solution.isValid()) {
						//System.out.println(new StringRenderer().render(solution));
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
