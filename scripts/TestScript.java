import org.apache.commons.lang3.ArrayUtils;

import avanwieringen.sudoku.Sudoku;
import avanwieringen.sudoku.renderer.StringRenderer;
import avanwieringen.sudoku.solver.Solver;
import avanwieringen.sudoku.solver.SolverResult;
import avanwieringen.sudoku.solver.strategy.BruteForceStrategy;
import avanwieringen.sudoku.solver.strategy.SimpleLogicStrategy;
import avanwieringen.sudoku.solver.strategy.StrategyInterface;


public class TestScript {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Sudoku s = new Sudoku(".98.1....2......6.............3.2.5..84.........6.........4.8.93..5...........1..");
		Sudoku s = new Sudoku("000004000100803907908200005807030200501009008000040500000102000000007100612300870");
		//Sudoku s = new Sudoku("800005216045862007670009500769204030020001765001670009004096800907400601306107940");
		StringRenderer renderer = new StringRenderer();
		System.out.println(renderer.render(s));
		
		Solver solver = new Solver(new StrategyInterface[] { new SimpleLogicStrategy(), new BruteForceStrategy() });
		SolverResult solution = solver.solve(s);
		System.out.println(renderer.render(solution.getSoduku()));
		System.out.println(solution.getResult().toString());
	}

}
