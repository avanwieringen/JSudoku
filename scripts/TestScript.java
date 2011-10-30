import java.io.File;
import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

import nl.falcon108.sudoku.Sudoku;
import nl.falcon108.sudoku.reader.FileReader;
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
		//Sudoku s = new Sudoku("..........5724...98....947...9..3...5..9..12...3.1.9...6....25....56.....7......6"); // very hard
		//Sudoku s = new Sudoku("000023000004000100050084090001070902093006000000010760000000000800000004060000587");
		//Sudoku s = new Sudoku("500200010001900730000000800050020008062039000000004300000000000080467900007300000");
		//Sudoku s = new Sudoku("028700050054003980000000007001090000006300000090004300000050000502000006600170009"); // xwing
		//Sudoku s = new Sudoku("261030845459010376783564009908003004605040902100600008300400081806020003500380007"); // swordfish
		//Sudoku s = new Sudoku("005160000600073000300005706000030691139756482862491007401000005000500008000007200"); // naked pair
		//Sudoku s = new Sudoku("700605029000800036001000074007008205090000483308000701279000658010789342800526917"); // naked triple
		//Sudoku s = new Sudoku("476050903132000085598000007800060009007405308300080002700000291280000576001070834"); // naked quad
		//Sudoku s = new Sudoku("010003008000500903000029000080000609279156834406000070000270000302001000600300090"); // hidden single
		//Sudoku s = new Sudoku("800070009010000050700608003380105274000207000200030005608409500100000006590000040"); // hidden pair
		//Sudoku s = new Sudoku("605301204703004509200000000152936847476812005938457621500000000807000402309108756"); // hidden triple
		//Sudoku s = new Sudoku("053108004060005008008200003000000000025800097709001060832010009004780006000000081"); // hidden quad
		//StringRenderer renderer = new StringRenderer(Type.SQUARE);
		//System.out.println(renderer.render(s));
		
		Solver solver = new Solver(new AbstractStrategy[] { 
					new NakedSinglesStrategy(),
					new HiddenSinglesStrategy(),
					new LockedCandidatesStrategy(),
					new NakedSubSetStrategy(NakedSubSetStrategy.Type.PAIR),
					new NakedSubSetStrategy(NakedSubSetStrategy.Type.TRIPLE),
					new NakedSubSetStrategy(NakedSubSetStrategy.Type.QUAD),
					new NakedSubSetStrategy(NakedSubSetStrategy.Type.QUINE),
					/**new HiddenSubSetStrategy(HiddenSubSetStrategy.Type.PAIR),
					new HiddenSubSetStrategy(HiddenSubSetStrategy.Type.TRIPLE),
					new HiddenSubSetStrategy(HiddenSubSetStrategy.Type.QUAD),**/
					new XWingStrategy(),
					new BruteForceStrategy(),
					
		});
		
		FileReader r = new FileReader(new File("input/gordon17.txt"), 1000);
		Sudoku[] sudokus = r.getSudokus();
		StringRenderer renderer = new StringRenderer(Type.LINE);
		SolverResult solution;
		
		System.out.println("Starting...");
		long start = System.currentTimeMillis();
		for(int i = 0; i < 1000; i++) {
			solution = solver.solve(sudokus[i]);
			//System.out.println(renderer.render(solution.getSoduku()));
		}
		long end = System.currentTimeMillis();
		System.out.println("Done in " + (end - start) + " milliseconds");
		
		
		//SolverResult solution = solver.solve(s);
		//System.out.println(renderer.render(solution.getSoduku()));
		//System.out.println(solution.getResult().name());
	}

}
