package nl.falcon108.tools;

public class Arrays {

	public static String convertToString(int[][] array) {
		StringBuilder b = new StringBuilder();
		for(int r = 0; r < array.length; r++) {
			for(int c = 0; c < array[r].length; c++) {
				b.append(array[r][c]);
				if(c < array[r].length) {
					b.append(' ');
				}
			}
			b.append("\n");
		}
		return b.toString();
	}
	
}
