package nl.falcon108.sudoku;

import java.util.Arrays;
import java.util.Vector;

import org.apache.commons.lang3.ArrayUtils;

public class Cell {

	/**
	 * The value of the cell
	 */
	protected int value;
	
	/**
	 * The maximum value of a cell
	 */
	protected int maxValue;
	
	/**
	 * A boolean array indicating which values are still possible
	 */
	protected boolean[] candidates;
	
	/** 
	 * The row to which this cell belongs
	 */
	protected House row;
	
	/**
	 * The column to which this cell belongs
	 */
	protected House column;
	
	/**
	 * The box to which this cell belongs
	 */
	protected House box;
	
	/**
	 * The peers of this cell in its row
	 */
	protected Cell[] rowPeers;
	
	/**
	 * The peers of this cell in its column
	 */
	protected Cell[] columnPeers;
	
	/**
	 * The peers of this cell in its box
	 */
	protected Cell[] boxPeers;
	
	/**
	 * All peers of this cell
	 */
	protected Cell[] peers;
	
	/**
	 * Empty contructor creating a standard Cell with a maximum value of 9 and no value
	 */
	public Cell() {
		this(0, 9);
	}
	
	/**
	 * Cell constructor
	 * @param value Value of the Cell, where 0 is no value
	 * @param maxValue Maximum possible value of the Cell
	 */
	public Cell(int value, int maxValue) {
		this.maxValue = maxValue;
		this.candidates = new boolean[this.maxValue];
		this.value = value;
		
		if(Math.pow(this.maxValue, 0.5) != (int)Math.pow(this.maxValue, 0.5)) {
			throw new IllegalArgumentException("MaxValue should be n^2");
		}
		
		if(value < 0 || value > this.maxValue) {
			throw new IndexOutOfBoundsException("Value must be between 0 (inclusive) and " + this.maxValue);
		}
		
		if(value == 0) {
			Arrays.fill(this.candidates, true);
		} else {
			Arrays.fill(this.candidates, false);
			this.candidates[value - 1] = true; 
		}
	}
	
	/**
	 * Returns the value of the cell if there is a unique possibility or 0 when there are more
	 * @return Value of the cell or 0 when there are more possibilities
	 */
	public int getValue() {
		//return this.getOccurences(this.possibilities, true) == 1 ? ArrayUtils.indexOf(this.possibilities, true) + 1 : 0;
		return this.value;
	}
	
	/**
	 * Sets the value of the current cell, removing all other possibilities and updating siblings
	 * @param value Value to set
	 */
	public void setValue(int value) {
		if(value < 0 || value > this.maxValue) {
			throw new IndexOutOfBoundsException("Value must be between 0 and " + this.maxValue);
		}
		
		if(value==0) {
			this.calculateCandidates();
		} else {
			if(!this.candidates[value - 1]) {
				throw new IndexOutOfBoundsException("Value " + value + " is no longer possible for this cell");
			}
			Arrays.fill(this.candidates, false);
			this.candidates[value - 1] = true;
			this.value = value;
			
			// update all peers
			//if(this.siblings != null) {
				for(Cell c : this.getPeers()) {
					c.removePossibility(value);
				}
			//}
		}
	}
	
	/**
	 * Returns an array with all the current candidates of the cell
	 * @return array with candidates
	 */
	public int[] getCandidates() {
		int[] possibilitiesInt 	= new int[this.getOccurences(this.candidates, true)];
		int currentIndex 	= 0;
		for(int i = 0; i < this.candidates.length; i++) {
			if(this.candidates[i]) {
				possibilitiesInt[currentIndex] = i + 1;
				currentIndex++;
			}
		}
		return possibilitiesInt;
	}
	
	public boolean hasCandidate(int candidate) {
		if(this.isFilled()) { return false; }
		return this.candidates[candidate - 1];
	}
	
	/**
	 * Reduces the candidates based on the values of all siblings
	 */
	public void calculateCandidates() {
		for (Cell c : this.getPeers()) {
			if(c.getValue() > 0) {
				this.candidates[c.getValue() - 1] = false;
			}
		}
	}
	
	/**
	 * Removes a possibility from the cell
	 * @param value The value to remove as possibility
	 */
	public void removePossibility(int value) {
		if(value <= 0 || value > this.maxValue) {
			throw new IndexOutOfBoundsException("Value must be between 0 and " + this.maxValue);
		}
		this.candidates[value - 1] = false;
	}
	
	/**
	 * Sets the row to which this cell belongs
	 * @param row
	 */
	public void setRow(House row) {
		this.row = row;
		if(!this.row.contains(this)) {
			this.row.addCell(this);
		}
	}
	
	/**
	 * Sets the column to which this cell belongs
	 * @param column
	 */
	public void setColumn(House col) {
		this.column = col;
		if(!this.column.contains(this)) {
			this.column.addCell(this);
		}
	}
	
	/**
	 * Sets the nonet to which this cell belongs
	 * @param box
	 */
	public void setBox(House non) {
		this.box = non;
		if(!this.box.contains(this)) {
			this.box.addCell(this);
		}
	}
	
	/**
	 * Gets the row to which this cell belongs
	 * @return CellCollection
	 */
	public House getRow() {
		return this.row;
	}
	
	/**
	 * Gets the column to which this cell belongs
	 * @return CellCollection
	 */
	public House getColumn() {
		return this.column;
	}
	
	/**
	 * Gets the box to which this cell belongs
	 * @return CellCollection
	 */
	public House getBox() {
		return this.box;
	}
	
	/**
	 * Returns whether or not this is still a solvable Cell
	 * @return boolean
	 */
	public boolean isValid() {
		return this.getCandidates().length > 0;
	}
	
	/**
	 * Returns whether or not this Cell is filled
	 * @return boolean
	 */
	public boolean isFilled() {
		//return this.getPossibilities().length == 1;
		return this.value > 0;
	}
	
	/**
	 * Returns the other Cells in this row
	 * @return Cell[]
	 */
	public Cell[] getRowPeers() {
		if(this.rowPeers == null) {
			this.rowPeers = this.getPeersFromCollection(this.row);
		}
		return this.rowPeers;
		//return this.getSiblingsFromCollection(this.row);
	}
	
	/**
	 * Returns the other Cells in this column
	 * @return Cell[]
	 */
	public Cell[] getColumnPeers() {
		if(this.columnPeers == null) {
			this.columnPeers = this.getPeersFromCollection(this.column);
		}
		return this.columnPeers;
		//return this.getSiblingsFromCollection(this.column);
	}
	
	/**
	 * Returns the other Cells in this nonet
	 * @return Cell[]
	 */
	public Cell[] getBoxPeers() {
		if(this.boxPeers == null) {
			this.boxPeers = this.getPeersFromCollection(this.box);
		}
		return this.boxPeers;
		//return this.getSiblingsFromCollection(this.nonet);
	}
	
	/**
	 * Returns all siblings from this cell
	 * @return Cell[] All siblings
	 */
	public Cell[] getPeers() {
		if(this.peers == null) {
			Cell[] row = this.getRowPeers();
			Cell[] col = this.getColumnPeers();
			Cell[] non = this.getBoxPeers();
			
			Vector<Cell> elements = new Vector<Cell>();
			for(int i = 0; i < row.length; i++) {
				if(!elements.contains(row[i])) { elements.add(row[i]); }
				if(!elements.contains(col[i])) { elements.add(col[i]); }
				if(!elements.contains(non[i])) { elements.add(non[i]); }
			}
			this.peers = elements.toArray(new Cell[0]);
			return elements.toArray(new Cell[0]);
		}
		return this.peers;
	}
	
	/**
	 * Returns the row index of this cell
	 * @return
	 */
	public int getRowIndex() {
		return this.row.getIndex();
	}
	
	/**
	 * Returns the column index of this cell
	 * @return
	 */
	public int getColumnIndex() {
		return this.column.getIndex();
	}
	
	/**
	 * Returns the box index of this cell
	 * @return
	 */
	public int getBoxIndex() {
		return this.box.getIndex();
	}
	
	@Override
	public String toString() {
		return "[Cell(" + this.getRowIndex() + "," + this.getColumnIndex() + "):" + String.valueOf(this.getValue()) + "]";
	}
	
	/**
	 * Auxiliary function to retrieve siblings from a general CellCollection
	 * @param cc CellCollection to retrieve siblings from
	 * @return Cell[]
	 */
	protected Cell[] getPeersFromCollection(House cc) {
		if(cc == null) {
			return new Cell[0];
		}
		Cell[] ret = new Cell[cc.getCells().length - 1];
		int i = 0;
		for (Cell c : cc.getCells()) {
			if(!c.equals(this)) {
				ret[i] = c;
				i++;
			}
		}
		return ret;
	}
	
	/**
	 * Auxiliary method to get the frequency of a specific value in an array
	 * @param array Array to be traversed
	 * @param value The value to search
	 * @return int
	 */
	protected int getOccurences(boolean[] array, boolean value) {
		int count = 0;
		for(int i = 0; i < array.length; i++) {
			if(array[i] == value) {
				count++;
			}
		}
		return count;
	}
}
