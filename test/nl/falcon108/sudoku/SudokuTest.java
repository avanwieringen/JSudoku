package nl.falcon108.sudoku;

import static org.junit.Assert.*;

import nl.falcon108.sudoku.House;
import nl.falcon108.sudoku.Sudoku;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import com.rits.cloning.Cloner;

public class SudokuTest {

	Sudoku s;
	Cloner cloner = new Cloner();
	
	@Test
	public void getValueFromSudoku1() {
		assertEquals(s.getValue(0, 2), 8);
	}
	
	@Test
	public void getValueFromSudoku2() {
		assertEquals(s.getValue(1, 1), 0);
	}
	
	@Test
	public void getRow() {
		assertEquals(s.getRow(1).getValue(0), 2);
	}
	
	@Test
	public void getColumn() {
		assertEquals(s.getColumn(1).getValue(0), 9);
	}
	
	@Test
	public void getNonet() {
		assertEquals(s.getBox(8).getValue(6), 1);
	}
	
	@Test
	public void relationTest1() {
		House row = s.getRow(8);
		House column = s.getColumn(8);
		
		s.setValue(8, 8, 4);
		assertEquals(row.getValue(8), column.getValue(8));
	}
	
	@Test
	public void relationTest2() {
		House row = s.getRow(8);
		House nonet = s.getBox(8);
		
		s.setValue(8, 8, 4);
		assertEquals(row.getValue(8), nonet.getValue(8));
	}
	
	@Test
	public void setValueTest1() {
		this.s = new Sudoku("000004000100803907908200005807030200501009008000040500000102000000007100612300870");		
		int[] possBefore = s.getPossibilities(7, 8);
		System.out.println(ArrayUtils.toString(possBefore));
		
		s.setValue(8, 8, 4);
		int[] possAfter  = s.getPossibilities(7, 8);
		System.out.println(ArrayUtils.toString(possAfter));
		
		assertEquals(possBefore.length - 1, possAfter.length);
	}
	
	@Test
	public void setValueTestWithClone() {
		this.s = new Sudoku("000004000100803907908200005807030200501009008000040500000102000000007100612300870");		
		Sudoku s2 = cloner.deepClone(s);
		s2.setValue(8, 8, 4);
		assertFalse("Values are not the same of clones", s2.getValue(8, 8) == s.getValue(8, 8));		
	}
	
	@Before
	public void createSudoku() {
		this.s = new Sudoku(".98.1....2......6.............3.2.5..84.........6.........4.8.93..5...........1..");
	}
	
}
