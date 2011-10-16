package avanwieringen.sudoku;

import org.apache.commons.lang3.ArrayUtils;

/**
 * A collection of cells representing the rows, columns and nonets of a Sudoku
 * @author arjan
 *
 */
public class House {
	
	/**
	 * Array of cell values in this collection
	 */
	protected Cell[] cells;
	
	/**
	 * Internal counter used for adding the cells
	 */
	protected int internalCounter = 0;
	
	/**
	 * The type of the collection (Row, Column or Nonet)
	 */
	protected Type type;
	
	/**
	 * The Sudoku this house belongs to
	 */
	protected Sudoku sudoku;
	
	/**
	 * Enumeration of the possible types
	 *
	 */
	public enum Type {
		ROW,
		COLUMN,
		BOX;
	}
	
	/**
	 * Constructs a new cell collection of specified size and type
	 * @param size The size of the cell collection
	 * @param type The type (Row, Column or Nonet)
	 */
	public House(int size, Type type) {
		this.cells = new Cell[size];
		this.type = type;
	}
	
	/**
	 * Adds a cell to the collection, where the cell is also updated such that there is a double reference
	 * @param c The cell to add
	 */
	public void addCell(Cell c) {
		if(this.internalCounter == this.cells.length) {
			throw new IndexOutOfBoundsException("Cannot add more cells, limit of " + this.cells.length + " ");
		}
		this.cells[this.internalCounter] = c;
		
		switch(this.type) {
			case ROW:
				c.setRow(this);
				break;
				
			case COLUMN:
				c.setColumn(this);
				break;
				
			case BOX:
				c.setBox(this);
				break;
		}
		this.internalCounter += 1;
	}
	
	/**
	 * Returns whether or not a Cell is contained in this collection
	 * @param c Cell
	 * @return boolean
	 */
	public boolean contains(Cell c) {
		return ArrayUtils.contains(this.cells, c);
	}
	
	/**
	 * Returns all Cells in this collection
	 * @return Cell[]
	 */
	public Cell[] getCells() {
		return this.cells;
	}
	
	/**
	 * Returns the value belonging to the specified index (0-based)
	 * @param i Index 0-based
	 * @return Value
	 */
	public int getValue(int i) {
		return getCell(i).getValue();
	}
	
	/**
	 * Returns the Cell belonging to the specified index (0-based)
	 * @param i Index 0-based
	 * @return Cell
	 */
	public Cell getCell(int i) {
		return this.cells[i];
	}
	
	/**
	 * Sets the Sudoku of this House
	 * @param s Sudoku
	 */
	public void setSudoku(Sudoku s) {
		this.sudoku = s;
	}
	
	
	/**
	 * Returns the Sudoku of this House
	 * @return Sudoku
	 */
	public Sudoku getSudoku() {
		return this.sudoku;
	}
	
	/**
	 * Returns the index of this House
	 * @return
	 */
	public int getIndex() {
		for(int i = 0; i < this.getSudoku().maxValue; i++) {
			switch(this.type) {
				case ROW:
					if(this.getSudoku().getRow(i).equals(this)) {
						return i;
					}
					break;
				case COLUMN:
					if(this.getSudoku().getColumn(i).equals(this)) {
						return i;
					}
					break;
				case BOX:
					if(this.getSudoku().getBox(i).equals(this)) {
						return i;
					}
					break;
			}
		}
		return -1;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[");
		switch(this.type) {
			case ROW:
				str.append("Row");
				break;
				
			case COLUMN:
				str.append("Column");
				break;
				
			case BOX:
				str.append("Box");
				break;
		}
		str.append("(" + this.getIndex() + "):{");
		for(Cell c : this.getCells()) {
			str.append(c.getValue() + ","); 
		}
		str.deleteCharAt(str.length() - 1);
		str.append("}]");
		return str.toString();
	}
}
