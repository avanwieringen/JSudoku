package avanwieringen.sudoku;

import org.apache.commons.lang3.ArrayUtils;

import com.sun.tools.corba.se.idl.InvalidArgument;

public class CellCollection {

	public static final int ROW_TYPE = 1;
	public static final int COLUMN_TYPE = 2;
	public static final int NONET_TYPE = 3;
	
	protected Cell[] cells;
	protected int internalCounter = 0;
	protected int type;
	
	public CellCollection(int size, int type) throws InvalidArgument {
		this.cells = new Cell[size];
		if(type < 1 || type > 3) {
			throw new InvalidArgument("Type must be CellCollection.ROW_TYPE, CellCollection.COLUMN_TYPE or CellCollection.NONET_TYPE");
		}
		this.type = type;
	}
	
	public void addCell(Cell c) {
		if(internalCounter == this.cells.length) {
			throw new IndexOutOfBoundsException("Cannot add more cells, limit of " + this.cells.length + " ");
		}
		this.cells[internalCounter] = c;
		
		switch(this.type) {
		case ROW_TYPE:
			c.setRow(this);
			break;
			
		case COLUMN_TYPE:
			c.setColumn(this);
			break;
			
		case NONET_TYPE:
			c.setNonet(this);
			break;
		}
		internalCounter++;
	}
	
	public boolean contains(Cell c) {
		return ArrayUtils.contains(this.cells, c);
	}
	
}
