package avanwieringen.sudoku;

import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

public class Cell {

	protected int maxValue;
	
	protected boolean[] possibilities;
	
	protected CellCollection row;
	
	protected CellCollection column;
	
	protected CellCollection nonet;
	
	public Cell() {
		this(0, 9);
	}
	
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
	
	public int getValue() {
		return this.getOccurences(this.possibilities, true) > 1 ? ArrayUtils.indexOf(this.possibilities, true) : 0;
	}
	
	public void setValue(int value) {
		if(value <= 0 || value > this.maxValue) {
			throw new IndexOutOfBoundsException("Value must be between 0 and " + this.maxValue);
		}
		Arrays.fill(this.possibilities, false);
		this.possibilities[value] = true;
	}
	
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
	
	public void removePossibility(int value) {
		if(value <= 0 || value > this.maxValue) {
			throw new IndexOutOfBoundsException("Value must be between 0 and " + this.maxValue);
		}
		this.possibilities[value] = false;
	}
	
	public void setRow(CellCollection row) {
		this.row = row;
		if(!this.row.contains(this)) {
			this.row.addCell(this);
		}
	}
	
	public void setColumn(CellCollection col) {
		this.column = col;
		if(!this.column.contains(this)) {
			this.column.addCell(this);
		}
	}
	
	public void setNonet(CellCollection non) {
		this.nonet = non;
		if(!this.nonet.contains(this)) {
			this.nonet.addCell(this);
		}
	}
	
	public CellCollection getRow() {
		return this.row;
	}
	
	public CellCollection getColumn() {
		return this.column;
	}
	
	public CellCollection getNonet() {
		return this.nonet;
	}
	
	public boolean isValid() {
		return this.getPossibilities().length > 0;
	}
	
	public boolean isFilled() {
		return this.getPossibilities().length == 1;
	}
	
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
