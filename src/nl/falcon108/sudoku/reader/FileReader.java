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
	
	public FileReader(File file) {
		this.file = file;
	}
	
	@Override
	public Sudoku[] getSudokus() {
		ArrayList<Sudoku> sudokus = new ArrayList<Sudoku>();
		try{
			FileInputStream fstream = new FileInputStream(this.file);
			DataInputStream in 		= new DataInputStream(fstream);
			BufferedReader br 		= new BufferedReader(new InputStreamReader(in));
			String strLine;
			
			while ((strLine = br.readLine()) != null)   {
				sudokus.add(new Sudoku(strLine));
			}
		} catch(FileNotFoundException exp) {
			System.out.println(exp.getMessage());
		} catch(IOException exp) {
			System.out.println(exp.getMessage());
		}
		
		return sudokus.toArray(new Sudoku[0]);
	}

}
