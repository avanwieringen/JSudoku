package avanwieringen.sudoku;

import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

public class Cell {

	protected int maxValue;
	protected boolean[] possibilities;
	
	public Cell() {
		this(0, 9);
	}
	
	public Cell(int value, int maxValue) {
		this.maxValue = maxValue;
		this.possibilities = new boolean[this.maxValue];
		
		if(value < 0 || value > this.maxValue) {
			throw new IndexOutOfBoundsException("Value must be between 0 and " + this.maxValue);
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
