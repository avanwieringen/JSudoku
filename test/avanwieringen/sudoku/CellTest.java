package avanwieringen.sudoku;

import static org.junit.Assert.*;

import org.junit.Test;

public class CellTest {
	
	@Test
	public void constructorDefault_1() {
		Cell c = new Cell();
		assertEquals(0,  c.getValue());
	}
	
	@Test
	public void constructorDefault_2() {
		Cell c = new Cell();
		assertEquals(9,  c.maxValue);
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void constructorFail_1() {
		new Cell(17, 16);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorFail_2() {
		new Cell(0, 8);
	}
	
	@Test
	public void getValue() {
		Cell c = new Cell();
		assertEquals(0, c.getValue());
	}
	
	@Test
	public void setValue() {
		Cell c = new Cell(1, 9);
		assertEquals(1, c.getValue());
	}
	
	@Test
	public void getPossibilities_1() {
		Cell c = new Cell(0,9);
		assertEquals(9, c.getPossibilities().length);
	}
	
	@Test
	public void getPossibilities_2() {
		Cell c = new Cell(0,9);
		c.setValue(3);
		assertEquals(1, c.getPossibilities().length);
	}
	
}
