import nl.falcon108.sudoku.Sudoku;
import nl.falcon108.sudoku.renderer.StringRenderer;
import nl.falcon108.sudoku.solver.Solver;
import nl.falcon108.sudoku.solver.SolverResult;
import nl.falcon108.sudoku.solver.strategy.*;

public class TestScript {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Sudoku s = new Sudoku(".3..1........427.3.2.9.6.4.5...2....349..............1.....9.....6....8...5..346.");
		//Sudoku s = new Sudoku("080020006000806000300000901409000000050307060000000805205000009000403000100070030");
		//Sudoku s = new Sudoku("800005216045862007670009500769204030020001765001670009004096800907400601306107940");
		//Sudoku s = new Sudoku("..........5724...98....947...9..3...5..9..12...3.1.9...6....25....56.....7......6"); // very hard
		//Sudoku s = new Sudoku("000023000004000100050084090001070902093006000000010760000000000800000004060000587");
		//Sudoku s = new Sudoku("500200010001900730000000800050020008062039000000004300000000000080467900007300000");
		//Sudoku s = new Sudoku("028700050054003980000000007001090000006300000090004300000050000502000006600170009"); // xwing
		Sudoku s = new Sudoku("005160000600073000300005706000030691139756482862491007401000005000500008000007200"); // naked pair
		StringRenderer renderer = new StringRenderer();
		System.out.println(renderer.render(s));
		
		Solver solver = new Solver(new AbstractStrategy[] { 
					new NakedSinglesStrategy(),
					new NakedSubSetStrategy(NakedSubSetStrategy.Type.PAIR)
					//new HiddenSinglesStrategy(),
					//new LockedCandidatesStrategy(),
					//new XWingStrategy(),
					//new BruteForceStrategy(),
		});
		SolverResult solution = solver.solve(s);
		System.out.println(renderer.render(solution.getSoduku()));
		System.out.println(solution.getResult().name());
	}

}
