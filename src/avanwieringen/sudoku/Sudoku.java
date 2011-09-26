package avanwieringen.sudoku;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author arjan
 *
 */
public class Sudoku {
	
	protected int[] values;
	
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
	 * @param values
	 */
	public Sudoku(String values) {
		for (char value : values.toCharArray()) {
			
		}
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
}
