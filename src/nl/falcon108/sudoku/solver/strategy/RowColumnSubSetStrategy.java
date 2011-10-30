package nl.falcon108.sudoku.solver.strategy;

import com.rits.cloning.Cloner;

import nl.falcon108.sudoku.Sudoku;

public class RowColumnSubSetStrategy extends AbstractStrategy {
	
	public enum Type {
		PAIR(2),
		TRIPLE(3),
		QUAD(4);
		
		private int size;

		Type(int size) {
			this.size = size;
		}
		
		public int getSize() {
			return this.size;
		}
	}
	
	Type type;

	Cloner cloner = new Cloner();
	
	/**
	 * Is the Sudoku changed by removing candidates?
	 */
	private boolean candidatesRemoved;
	
	public RowColumnSubSetStrategy(Type t) {
		this.type = t;
	}
	
	@Override
	public Sudoku[] solve(Sudoku s) {
		this.candidatesRemoved = false;
		Sudoku n = cloner.deepClone(s);
		
		/**for(int d = 1; d <= n.getBoxCount(); d++) {
			traverseHouses(n.getRows(), d, TraverseType.ROW);
			traverseHouses(n.getColumns(), d, TraverseType.COLUMN);
		}**/
		
		if(candidatesRemoved) {
			return new Sudoku[] { n };
		} else {
			return new Sudoku[0];
		}
	}

}
