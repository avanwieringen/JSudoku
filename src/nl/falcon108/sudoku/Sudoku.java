package nl.falcon108.sudoku;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author arjan
 *
 */
public class Sudoku {
	
	/**
	 * Values of the Sudoku
	 */
	protected Cell[][] cells;
	
	/**
	 * The maximum value of a cell (equal to the row-, column- and box count)
	 */
	protected int maxValue;
	
	/**
	 * The rows of the Sudoku
	 */
	protected House[] rows;
	
	/**
	 * The columns of the Sudoku
	 */
	protected House[] columns;
	
	/**
	 * The boxes of the Sudoku
	 */
	protected House[] boxes;
	
	/**
	 * 
	 */
	protected boolean possibilitiesReduced = false;

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
		
		// fill values and create relations
		this.maxValue 		= (int)Math.pow(values.length(), 0.5);
		this.cells 			= new Cell[this.maxValue][this.maxValue];
		this.rows			= new House[this.maxValue];
		this.columns 		= new House[this.maxValue];
		this.boxes 			= new House[this.maxValue];
		
		int row;
		int column;
		int box;
		Cell cell;
		
		// create relations
		for(int i = 0; i < values.length(); i++) {
			
			if(i >= 0 && i < this.maxValue) {
				this.rows[i] 	= new House(this.maxValue, House.Type.ROW);
				this.columns[i] = new House(this.maxValue, House.Type.COLUMN);
				this.boxes[i] 	= new House(this.maxValue, House.Type.BOX);
				
				this.rows[i].setSudoku(this);
				this.columns[i].setSudoku(this);
				this.boxes[i].setSudoku(this);
			}
			
			row 	= (int)(i/this.maxValue);
			column 	= (int)(i%this.maxValue);
			box  	= (int) (((int)(row/Math.sqrt(this.maxValue)) * Math.sqrt(this.maxValue)) + ((int)(column / Math.sqrt(this.maxValue))));
			cell    = new Cell();
			this.cells[row][column] = cell;
			this.rows[row].addCell(cell);
			this.columns[column].addCell(cell);
			this.boxes[box].addCell(cell);
		}
		
		// fill values
		for(int i = 0; i < values.length(); i++) {
			row 	= (int)(i/this.maxValue);
			column 	= (int)(i%this.maxValue);
			box  	= (int) (((int)(row/Math.sqrt(this.maxValue)) * Math.sqrt(this.maxValue)) + ((int)(column / Math.sqrt(this.maxValue))));
			this.setValue(row, column, this.parseValue(values.charAt(i)));
		}
	}

	/**
	 * Sets a value of a specified cell
	 * @param r The row-index (0-based)
	 * @param c The column (0-based)
	 * @param value The value to set
	 */
	public void setValue(int r, int c, int value) {
		this.cells[r][c].setValue(value);
	}
	
	/**
	 * Returns the value belonging to the specified row and column (0-based)
	 * @param r Row-index 0-based
	 * @param c Column-index 0-based
	 * @return Value
	 */
	public int getValue(int r, int c) {
		return getCell(r, c).getValue();
	}
	
	/**
	 * Returns the Cell belonging to the specified row and column (0-based)
	 * @param r Row-index 0-based
	 * @param c Column-index 0-based
	 * @return Cell
	 */
	public Cell getCell(int r, int c) {
		return this.cells[r][c];
	}
	
	/**
	 * Returns the row count
	 * @return Row count
	 */
	public int getRowCount() {
		return this.maxValue;
	}
	
	/**
	 * Returns the row House belonging to the specified row-index (0-based)
	 * @param r Row-index 0-based
	 * @return CellCollection
	 */
	public House getRow(int r) {
		return this.rows[r];
	}
	
	/**
	 * Return the rows of this Sudoku
	 * @return House Array
	 */
	public House[] getRows() {
		return this.rows;
	}
	
	/**
	 * Returns the Column count
	 * @return Column count
	 */
	public int getColumnCount() {
		return this.maxValue;
	}
	
	/**
	 * Returns the column House belonging to the specified column-index (0-based)
	 * @param r column-index 0-based
	 * @return CellCollection
	 */
	public House getColumn(int c) {
		return this.columns[c];
	}
	
	/**
	 * Return the columns of this Sudoku
	 * @return House Array
	 */
	public House[] getColumns() {
		return this.columns;
	}
	
	/**
	 * Returns the box count
	 * @return Box count
	 */
	public int getBoxCount() {
		return this.maxValue;
	}
	
	/**
	 * Returns the box House belonging to the specified box-index (0-based)
	 * @param r Box-index 0-based
	 * @return CellCollection
	 */
	public House getBox(int n) {
		return this.boxes[n];
	}
	
	/**
	 * Return the boxes of this Sudoku
	 * @return House Array
	 */
	public House[] getBoxes() {
		return this.boxes;
	}
	
	/**
	 * Returns the total cell count
	 * @return Cell count
	 */
	public int getCellCount() {
		return (int) Math.pow(this.maxValue, 2);
	}
	
	/**
	 * Calculates the filled cells
	 * @return Filled Cell count
	 */
	public int getFilledCells() {
		int i = 0;
		for(int r = 0; r < this.getRowCount(); r++) {
			for(int c = 0; c < this.getColumnCount(); c++) {
				if(this.getCell(r, c).isFilled()) {
					i++;
				}
			}
		}
		return i;
	}
	
	/**
	 * Returns an array with all the current possibilities of a cell
	 * @return array with possibilities
	 */
	public int[] getPossibilities(int r, int c) {
		return this.getCell(r, c).getCandidates();
	}
	
	/**
	 * Returns wether or not the Sudoku is solvable
	 * @return boolean solvable
	 */
	public boolean isValid() {
		for(int r = 0; r < this.getRowCount(); r++) {
			for(int c = 0; c < this.getColumnCount(); c++) {
				if(!this.getCell(r, c).isValid()) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Returns wether or not the Sudoku is solved
	 * @return boolean solved
	 */
	public boolean isSolved() {
		for(int r = 0; r < this.getRowCount(); r++) {
			for(int c = 0; c < this.getColumnCount(); c++) {
				//if(this.getCell(r, c).getPossibilities().length > 1) {
				if(!this.getCell(r, c).isFilled()) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Reduces all possibilities for each Cell
	 */
	/**public void reducePossibilities() {
		for(int r = 0; r < this.cells.length; r++) {
			for(int c = 0; c < this.cells[r].length; c++) {
				this.cells[r][c].calculateCandidates();
			}
		}
		this.possibilitiesReduced = true;
	}
	
	public boolean possibilitiesReduced() {
		return this.possibilitiesReduced;
	}**/
	
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
}
