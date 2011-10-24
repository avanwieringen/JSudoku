package nl.falcon108.sudoku.solver.strategy;

import java.util.Vector;

import nl.falcon108.sudoku.Cell;
import nl.falcon108.sudoku.House;
import nl.falcon108.sudoku.Sudoku;

import org.apache.commons.lang3.ArrayUtils;

import com.rits.cloning.Cloner;


public class NakedSubSetStrategy extends AbstractStrategy {

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
	
	/**
	 * Which NakedSubSetStrategy type?
	 * @param Type
	 */
	public NakedSubSetStrategy(Type t) {
		this.type = t;
	}
	
	@Override
	public Sudoku[] solve(Sudoku s) {
		this.candidatesRemoved = false;
		Sudoku n = cloner.deepClone(s);
		
		this.traverseHouses(n.getBoxes());
		//this.traverseHouses(n.getRows());
		//this.traverseHouses(n.getColumns());
		
		if(candidatesRemoved) {
			return new Sudoku[] { n };
		} else {
			return new Sudoku[0];
		}		
	}
	
	private void traverseHouses(House[] hs) {
		Vector<int[]> sets;
		Vector<int[]> commonSets;
		for(House h : hs) {
			sets = new Vector<int[]>();
			
			for(Cell c : h.getCells()) {
				if(!c.isFilled() && c.getCandidates().length >= 2 && c.getCandidates().length <= this.type.getSize()) {
					sets.add(c.getCandidates());
				}
			}
			
		}
	}
	
}
