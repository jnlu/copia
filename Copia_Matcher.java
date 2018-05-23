package copia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class Copia_Matcher {
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
	
		File customers = new File("/Users/joshualu/Desktop/Copia/Customers.csv");
		File recipients = new File("/Users/joshualu/Desktop/Copia/Recipients.csv");
		String output = "/Users/joshualu/Desktop/Copia/Output/";
		
		Main.match(Parser.parseCSV(customers), Parser.parseCSV(recipients), output);
	
	}

}
