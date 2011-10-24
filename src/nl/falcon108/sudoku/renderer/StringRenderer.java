package nl.falcon108.sudoku.renderer;

import nl.falcon108.sudoku.Sudoku;

public class StringRenderer {

	public String render(Sudoku s) {
		StringBuilder b = new StringBuilder();
		for(int r = 0; r < s.getRowCount(); r++) {
			for(int c = 0; c < s.getColumnCount(); c++) {
				b.append(s.getValue(r, c) == 0 ? "." : s.getValue(r,c));
				if(c < s.getColumnCount()) {
					b.append(' ');
				}
			}
			b.append("\n");
		}
		return b.toString();
	}
	
}
