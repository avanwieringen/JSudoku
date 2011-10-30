import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

import nl.falcon108.sudoku.Sudoku;
import nl.falcon108.sudoku.renderer.StringRenderer;
import nl.falcon108.sudoku.renderer.StringRenderer.Type;
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
		Sudoku s = new Sudoku("..........5724...98....947...9..3...5..9..12...3.1.9...6....25....56.....7......6"); // very hard
		//Sudoku s = new Sudoku("000023000004000100050084090001070902093006000000010760000000000800000004060000587");
		//Sudoku s = new Sudoku("500200010001900730000000800050020008062039000000004300000000000080467900007300000");
		//Sudoku s = new Sudoku("028700050054003980000000007001090000006300000090004300000050000502000006600170009"); // xwing
		//Sudoku s = new Sudoku("005160000600073000300005706000030691139756482862491007401000005000500008000007200"); // naked pair
		//Sudoku s = new Sudoku("700605029000800036001000074007008205090000483308000701279000658010789342800526917"); // naked triple
		//Sudoku s = new Sudoku("476050903132000085598000007800060009007405308300080002700000291280000576001070834"); // naked quad
		StringRenderer renderer = new StringRenderer(Type.SQUARE);
		System.out.println(renderer.render(s));
		
		Solver solver = new Solver(new AbstractStrategy[] { 
					new NakedSinglesStrategy(),
					new HiddenSinglesStrategy(),
					new LockedCandidatesStrategy(),
					new NakedSubSetStrategy(NakedSubSetStrategy.Type.PAIR),
					new NakedSubSetStrategy(NakedSubSetStrategy.Type.TRIPLE),
					new NakedSubSetStrategy(NakedSubSetStrategy.Type.QUAD),
					new XWingStrategy(),
					new BruteForceStrategy(),
		});
		SolverResult solution = solver.solve(s);
		System.out.println(renderer.render(solution.getSoduku()));
		System.out.println(solution.getResult().name());
		
		//Combinations.generate(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8}, 2);
		//ArrayList<ArrayList<Integer>> combs = Combinations.generate(new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8}, 4);
		//for(ArrayList<Integer> c : combs) {
		//	System.out.println(ArrayUtils.toString(c));
		//}
		//Combinations.generate(new Integer[] {1, 2}, 2);
	}

}
