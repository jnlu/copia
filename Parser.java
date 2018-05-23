package copia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
	
	public static ArrayList<String[]> parseCSV(File file) throws FileNotFoundException, IOException {
		ArrayList<String[]> entries = new ArrayList<String[]>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line = br.readLine();
		    while ((line = br.readLine()) != null) {
		    	entries.add(line.split(",", 0));
		    }
		}
		return entries;
	}

}
