package nl.falcon108.sudoku.reader;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import nl.falcon108.sudoku.Sudoku;

public class FileReader implements ReaderInterface {

	private File file;
	private int maxCapacity;
	
	public FileReader(File file) {
		this(file, 1000);
	}
	
	public FileReader(File file, int maxCapacity) {
		this.file = file;
		this.maxCapacity = maxCapacity;
	}
	
	@Override
	public Sudoku[] getSudokus() {
		ArrayList<Sudoku> sudokus = new ArrayList<Sudoku>();
		try{
			FileInputStream fstream = new FileInputStream(this.file);
			DataInputStream in 		= new DataInputStream(fstream);
			BufferedReader br 		= new BufferedReader(new InputStreamReader(in));
			String strLine;
			int i = 0;
			while ((strLine = br.readLine()) != null && i < this.maxCapacity)   {
				sudokus.add(new Sudoku(strLine));
				i++;
			}
		} catch(FileNotFoundException exp) {
			System.out.println(exp.getMessage());
		} catch(IOException exp) {
			System.out.println(exp.getMessage());
		}
		
		return sudokus.toArray(new Sudoku[0]);
	}

}
