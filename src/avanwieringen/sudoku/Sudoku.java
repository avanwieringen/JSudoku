package avanwieringen.sudoku;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

import avanwieringen.tools.*;

/**
 * 
 * @author arjan
 *
 */
public class Sudoku {
	
	/**
	 * Values of the Sudoku
	 */
	protected Cell[][] values;
	
	/**
	 * Array specifying for each row the indices of the cells belonging to it
	 */
	protected int[][] rowRelations;
	
	/**
	 * Array specifying for each column the indices of the cells belonging to it
	 */
	protected int[][] colRelations;
	
	/**
	 * Array specifying for each nonet the indices of the cells belonging to it
	 */
	protected int[][] nonRelations;
	
	/**
	 * The maximum value of a cell (equal to the row-, column- and nonet count)
	 */
	protected int maxValue;

	/**
	 * Construct an empty 9^2 * 9^2 Sudoku
	 */
	public Sudoku() {
		this(9);
	}
	
	/**
	 * Creates an empty n^2 x n^2 Sudoku
	 * @param n
	 */
	public Sudoku(int n) {
		this(StringUtils.repeat('.', (int) Math.pow(n, 4)));
	}
	
	/**
	 * Create a Sudoku of the given values
	 * @param values A String of 81 characters specifying the values
	 */
	public Sudoku(String values) {
		if(Math.pow(values.length(), 0.25) != (int)Math.pow(values.length(), 0.25)) {
			throw new IllegalArgumentException("Values should be a String of n^2 * n^2 characters");
		}
		
		// fill values and possibilities
		this.maxValue 		= (int)Math.pow(values.length(), 0.25);
		this.values 		= new Cell[this.maxValue][this.maxValue];
		
		for(int i = 0; i < values.length(); i++) {
			this.setValue(this.getRowNumberFromIndex(i), this.getRowNumberFromIndex(i), this.parseValue(values.charAt(i)));
		}
		
		// fill relations
		/**this.rowRelations 	= new int[this.maxValue][this.maxValue];
		this.colRelations 	= new int[this.maxValue][this.maxValue];
		this.nonRelations 	= new int[this.maxValue][this.maxValue];
		for(int i = 0; i < this.maxValue; i++) {
			for(int j = 0; j < this.maxValue; j++) {
				this.rowRelations[i][j] = (i*this.maxValue + j);
				this.colRelations[i][j] = (i + j*this.maxValue);
				this.nonRelations[i][j] = (int) (i*Math.sqrt(this.maxValue) + j%Math.sqrt(this.maxValue) + (int)(j/Math.sqrt(this.maxValue))*this.maxValue);
			}			
		}**/
	}
	
	/**
	 * Returns the value belonging to the specified row and column (0-based)
	 * @param r Row-index 0-based
	 * @param c Column-index 0-based
	 * @return Value
	 */
	public int getValue(int r, int c) {
		return this.values[r][c].getValue();
	}
	
	/**
	 * Returns the row count
	 * @return Row count
	 */
	public int getRowCount() {
		return this.maxValue;
	}
	
	/**
	 * Returns the Column count
	 * @return Column count
	 */
	public int getColumnCount() {
		return this.maxValue;
	}
	
	/**
	 * Returns the nonet count
	 * @return Nonet count
	 */
	public int getNonetCount() {
		return this.maxValue;
	}
	
	/**
	 * Returns the total cell count
	 * @return Cell count
	 */
	public int getCellCount() {
		return (int) Math.pow(this.maxValue, 2);
	}
	
	/**
	 * Gets the 1-dimensional index belonging to the specified row and column (0-based)
	 * @param r Row-index 0-based
	 * @param c Column-index 0-based
	 * @return 1-dimensional index
	 */
	protected int getIndex(int r, int c) {
		if(r >= this.maxValue || c >= this.maxValue) {
			throw new IndexOutOfBoundsException("Row and column indices must be < " + this.maxValue);
		}
		return r*this.maxValue+c;
	}
	
	/**
	 * Parses a char value to the corresponding integer value, counting '.', '0', ' ', '-' and'x' as empty values
	 * @param value
	 * @return the parsed value
	 */
	protected int parseValue(char value) {
		char[] emptyValues = {'.', '0', ' ', '-', 'x'};
		if(ArrayUtils.contains(emptyValues, value)) {
			return 0;
		} else {
			return CharUtils.toIntValue(value);
		}
	}
	
	/**
	 * Parses an int value to the corresponding integer value
	 * @param value
	 * @return the parsed value
	 */
	protected int parseValue(int value) {
		if(value > this.maxValue || value < 0) {
			throw new IndexOutOfBoundsException("Values must lie between 0 (empty) or " + this.maxValue);
		}
		return value;
	}
	
	/**
	 * Gets the row number (0-based) belonging to the specified 1-dimensional index
	 * @param i 1-dimensional index
	 * @return Row number (0-based)
	 */
	protected int getRowNumberFromIndex(int i) {
		return (int)(i/this.maxValue);
	}
	
	/**
	 * Gets the column number (0-based) belonging to the specified 1-dimensional index
	 * @param i 1-dimensional index
	 * @return Column number (0-based)
	 */
	protected int getColumnNumberFromIndex(int i) {
		return (int)(i%this.maxValue);
	}
	
	/**
	 * Gets the nonet number (0-based) belonging to the specified 1-dimensional index
	 * @param i 1-dimensional index
	 * @return Nonet number (0-based)
	 */
	protected int getNonetNumberFromIndex(int i) {
		int r = this.getRowNumberFromIndex(i);
		int c = this.getColumnNumberFromIndex(i);
		return  (int) (((int)(r/Math.sqrt(this.maxValue)) * Math.sqrt(this.maxValue)) + ((int)(c / Math.sqrt(this.maxValue))));
	}
}
