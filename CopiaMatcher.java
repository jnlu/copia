package copia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CopiaMatcher {
	
	// arg[0] is the folder containing the customer and recipient CSV files
	// arg[1] is the folder where you want the output CSV files to be printed
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
	
		// Open the files, parses them, matches recipients and customers, then prints the results
		
		File customers = new File(args[0] + "Customers.csv");
		File recipients = new File(args[0] + "Recipients.csv");
		Map.Entry<HashMap<String, Integer>, HashMap<String, ArrayList<Map.Entry<String, Double>>>> maps = Matcher.match(Parser.parseCSV(customers), Parser.parseCSV(recipients));
		
		HashMap<String, Integer> pop = maps.getKey();
		HashMap<String, ArrayList<Map.Entry<String, Double>>> matches = maps.getValue();
		
		Printer.print(args[1], pop, matches);
	
	}

}
