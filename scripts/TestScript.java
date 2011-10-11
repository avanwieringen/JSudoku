import org.apache.commons.lang3.ArrayUtils;

import avanwieringen.sudoku.Sudoku;
import avanwieringen.sudoku.renderer.StringRenderer;
import avanwieringen.sudoku.solver.Solver;
import avanwieringen.sudoku.solver.SolverResult;
import avanwieringen.sudoku.solver.strategy.BruteForceStrategy;
import avanwieringen.sudoku.solver.strategy.HiddenSinglesStrategy;
import avanwieringen.sudoku.solver.strategy.NakedSinglesStrategy;
import avanwieringen.sudoku.solver.strategy.StrategyInterface;


public class TestScript {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Sudoku s = new Sudoku(".3..1........427.3.2.9.6.4.5...2....349..............1.....9.....6....8...5..346.");
		Sudoku s = new Sudoku("080020006000806000300000901409000000050307060000000805205000009000403000100070030");
		//Sudoku s = new Sudoku("800005216045862007670009500769204030020001765001670009004096800907400601306107940");
		StringRenderer renderer = new StringRenderer();
		System.out.println(renderer.render(s));
		
		Solver solver = new Solver(new StrategyInterface[] { 
					new NakedSinglesStrategy(), 
					new HiddenSinglesStrategy(), 
					new BruteForceStrategy(),
		});
		SolverResult solution = solver.solve(s);
		System.out.println(renderer.render(solution.getSoduku()));
		System.out.println(solution.getResult().toString());
	}

}
