package avanwieringen.sudoku;

import java.util.Arrays;
import java.util.Vector;

import org.apache.commons.lang3.ArrayUtils;

public class Cell {

	/**
	 * The maximum value of a cell
	 */
	protected int maxValue;
	
	/**
	 * A boolean array indicating which values are still possible
	 */
	protected boolean[] possibilities;
	
	/** 
	 * The row to which this cell belongs
	 */
	protected CellCollection row;
	
	/**
	 * The column to which this cell belongs
	 */
	protected CellCollection column;
	
	/**
	 * The nonet to which this cell belongs
	 */
	protected CellCollection nonet;
	
	/**
	 * The siblings of this cell in its row
	 */
	protected Cell[] rowSiblings;
	
	/**
	 * The siblings of this cell in its column
	 */
	protected Cell[] columnSiblings;
	
	/**
	 * The siblings of this cell in its nonet
	 */
	protected Cell[] nonetSiblings;
	
	/**
	 * All siblings of this cell
	 */
	protected Cell[] siblings;
	
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
		this.possibilities = new boolean[this.maxValue];
		
		if(value < 0 || value > this.maxValue) {
			throw new IndexOutOfBoundsException("Value must be between 0 (inclusive) and " + this.maxValue);
		}
		
		if(value == 0) {
			Arrays.fill(this.possibilities, true);
		} else {
			Arrays.fill(this.possibilities, false);
			this.possibilities[value - 1] = true; 
		}
	}
	
	/**
	 * Returns the value of the cell if there is a unique possibility or 0 when there are more
	 * @return Value of the cell or 0 when there are more possibilities
	 */
	public int getValue() {
		return this.getOccurences(this.possibilities, true) == 1 ? ArrayUtils.indexOf(this.possibilities, true) + 1 : 0;
	}
	
	/**
	 * Sets the value of the current cell, removing all other possibilities and updating siblings
	 * @param value Value to set
	 */
	public void setValue(int value) {
		if(value <= 0 || value > this.maxValue) {
			throw new IndexOutOfBoundsException("Value must be between 0 and " + this.maxValue);
		}
		Arrays.fill(this.possibilities, false);
		this.possibilities[value - 1] = true;
		
		// update all siblings
		for(Cell c : this.getSiblings()) {
			c.removePossibility(value);
		}
	}
	
	/**
	 * Returns an array with all the current possibilities of the cell
	 * @return array with possibilities
	 */
	public int[] getPossibilities() {
		int[] possibilitiesInt 	= new int[this.getOccurences(this.possibilities, true)];
		int currentIndex 	= 0;
		for(int i = 0; i < this.possibilities.length; i++) {
			if(this.possibilities[i]) {
				possibilitiesInt[currentIndex] = i + 1;
				currentIndex++;
			}
		}
		return possibilitiesInt;
	}
	
	/**
	 * Reduces the possibilities based on the values of all siblings
	 */
	public void calculatePossibilities() {
		for (Cell c : this.getSiblings()) {
			if(c.getValue() > 0) {
				this.possibilities[c.getValue() - 1] = false;
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
		this.possibilities[value - 1] = false;
	}
	
	/**
	 * Sets the row to which this cell belongs
	 * @param row
	 */
	public void setRow(CellCollection row) {
		this.row = row;
		if(!this.row.contains(this)) {
			this.row.addCell(this);
		}
	}
	
	/**
	 * Sets the column to which this cell belongs
	 * @param column
	 */
	public void setColumn(CellCollection col) {
		this.column = col;
		if(!this.column.contains(this)) {
			this.column.addCell(this);
		}
	}
	
	/**
	 * Sets the nonet to which this cell belongs
	 * @param nonet
	 */
	public void setNonet(CellCollection non) {
		this.nonet = non;
		if(!this.nonet.contains(this)) {
			this.nonet.addCell(this);
		}
	}
	
	/**
	 * Gets the row to which this cell belongs
	 * @return CellCollection
	 */
	public CellCollection getRow() {
		return this.row;
	}
	
	/**
	 * Gets the column to which this cell belongs
	 * @return CellCollection
	 */
	public CellCollection getColumn() {
		return this.column;
	}
	
	/**
	 * Gets the nonet to which this cell belongs
	 * @return CellCollection
	 */
	public CellCollection getNonet() {
		return this.nonet;
	}
	
	/**
	 * Returns whether or not this is still a solvable Cell
	 * @return boolean
	 */
	public boolean isValid() {
		return this.getPossibilities().length > 0;
	}
	
	/**
	 * Returns whether or not this Cell is filled
	 * @return boolean
	 */
	public boolean isFilled() {
		return this.getPossibilities().length == 1;
	}
	
	/**
	 * Returns the other Cells in this row
	 * @return Cell[]
	 */
	public Cell[] getRowSiblings() {
		if(this.rowSiblings == null) {
			this.rowSiblings = this.getSiblingsFromCollection(this.row);
		}
		return this.rowSiblings;
	}
	
	/**
	 * Returns the other Cells in this column
	 * @return Cell[]
	 */
	public Cell[] getColumnSiblings() {
		if(this.columnSiblings == null) {
			this.columnSiblings = this.getSiblingsFromCollection(this.column);
		}
		return this.columnSiblings;
	}
	
	/**
	 * Returns the other Cells in this nonet
	 * @return Cell[]
	 */
	public Cell[] getNonetSiblings() {
		if(this.nonetSiblings == null) {
			this.nonetSiblings = this.getSiblingsFromCollection(this.nonet);
		}
		return this.nonetSiblings;
	}
	
	/**
	 * Returns all siblings from this cell
	 * @return Cell[] All siblings
	 */
	public Cell[] getSiblings() {
		if(this.siblings == null) {
			Cell[] row = this.getRowSiblings();
			Cell[] col = this.getColumnSiblings();
			Cell[] non = this.getNonetSiblings();
			
			Vector<Cell> elements = new Vector<Cell>();
			for(int i = 0; i < row.length; i++) {
				if(!elements.contains(row[i])) { elements.add(row[i]); }
				if(!elements.contains(col[i])) { elements.add(col[i]); }
				if(!elements.contains(non[i])) { elements.add(non[i]); }
			}
			this.siblings = (Cell[]) elements.toArray();
		}
		return this.siblings;
	}
	
	/**
	 * Auxiliary function to retrieve siblings from a general CellCollection
	 * @param cc CellCollection to retrieve siblings from
	 * @return Cell[]
	 */
	protected Cell[] getSiblingsFromCollection(CellCollection cc) {
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
