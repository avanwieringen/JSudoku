package avanwieringen.sudoku;

import java.util.Arrays;
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
			this.possibilities[value] = true; 
		}
	}
	
	/**
	 * Returns the value of the cell if there is a unique possibility or 0 when there are more
	 * @return Value of the cell or 0 when there are more possibilities
	 */
	public int getValue() {
		return this.getOccurences(this.possibilities, true) == 1 ? ArrayUtils.indexOf(this.possibilities, true) : 0;
	}
	
	/**
	 * Sets the value of the current cell, removing all other possibilities
	 * @param value Value to set
	 */
	public void setValue(int value) {
		if(value <= 0 || value > this.maxValue) {
			throw new IndexOutOfBoundsException("Value must be between 0 and " + this.maxValue);
		}
		Arrays.fill(this.possibilities, false);
		this.possibilities[value] = true;
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
				possibilitiesInt[currentIndex] = i;
				currentIndex++;
			}
		}
		return possibilitiesInt;
	}
	
	/**
	 * Removes a possibility from the cell
	 * @param value The value to remove as possibility
	 */
	public void removePossibility(int value) {
		if(value <= 0 || value > this.maxValue) {
			throw new IndexOutOfBoundsException("Value must be between 0 and " + this.maxValue);
		}
		this.possibilities[value] = false;
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
