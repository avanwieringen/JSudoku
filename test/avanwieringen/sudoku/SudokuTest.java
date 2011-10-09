package avanwieringen.sudoku;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

public class SudokuTest {

	Sudoku s;
	
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
		assertEquals(s.getNonet(8).getValue(6), 1);
	}
	
	@Test
	public void relationTest1() {
		CellCollection row = s.getRow(8);
		CellCollection column = s.getColumn(8);
		
		s.setValue(8, 8, 4);
		assertEquals(row.getValue(8), column.getValue(8));
	}
	
	@Test
	public void relationTest2() {
		CellCollection row = s.getRow(8);
		CellCollection nonet = s.getNonet(8);
		
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
		int[] possBefore = s.getPossibilities(7, 8);
		System.out.println(ArrayUtils.toString(possBefore));
		
		int[] possAfter = new int[0];
		try {
			Sudoku s2;
			s2 = (Sudoku) s.clone();
			s2.setValue(8, 8, 4);
			possAfter  = s2.getPossibilities(7, 8);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		System.out.println(ArrayUtils.toString(possAfter));		
		assertEquals(possBefore.length - 1, possAfter.length);
	}
	
	@Before
	public void createSudoku() {
		this.s = new Sudoku(".98.1....2......6.............3.2.5..84.........6.........4.8.93..5...........1..");
	}
	
}
