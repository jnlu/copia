package copia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CopiaMatcher {
	
	// arg[0] is the folder containing the customer and recipient CSV files. arg[1] is the folder where you want the output CSV files to be printed
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
	
		// Open the files, parses them, then matches recipients and customers
		// Returns an entry (tuple equivalent); the key is a map that maps each recipient and how many customers they match with, 
		// and the value is a map that maps each customer and the list of recipients they're compatible with
		File customers = new File(args[0] + "Customers.csv");
		File recipients = new File(args[0] + "Recipients.csv");
		Map.Entry<HashMap<String, Integer>, HashMap<String, ArrayList<Map.Entry<String, Double>>>> maps = Matcher.match(Parser.parseCSV(customers), Parser.parseCSV(recipients));
		
		HashMap<String, Integer> pop = maps.getKey();
		HashMap<String, ArrayList<Map.Entry<String, Double>>> matches = maps.getValue();
		
		// Loops through each match and prints a CSV file for each customer that has their compatible recipients
		
		ArrayList<String> names = new ArrayList<String>();
		for (String key : matches.keySet()) {
			
			// Makes a new CSV file for each customer using their last and first names
			String[] c_split = key.split(",");
			String customer = c_split[0] + "_" + c_split[1];
			
			// Adds a counter if the customer's name is already in a file
			if(names.contains(customer)) {
				int counter = 0;
				while (names.contains(customer + counter)) {
					counter++;
				}
			}
			String filename = args[1] + customer + ".csv";
			names.add(customer);
			FileWriter filewriter = new FileWriter(new File(filename));
			PrintWriter printwriter = new PrintWriter(filewriter);
			
			// Same header as the recipients CSV but with popularity and distance added at the end
			// Popularity is how many total customers each recipient is compatible with, and distance is how far away the customer is from the recipient
			printwriter.print("FirstName,LastName,Street,City,State,Postal,Country,Email,Phone,Latitude,Longitude,Restrictions,Sunday,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Popularity,Distance\n");
			
			// Sorts the array based on popularity, then distance
			ArrayList<Map.Entry<String, Double>> curr_matches = Matcher.sort(key, pop, matches);
			
			// Prints the recipients
			for (int i = 0; i < curr_matches.size(); i++) {
				String k = curr_matches.get(i).getKey();
				printwriter.print(k + "," + pop.get(k) + "," + (double)Math.round(curr_matches.get(i).getValue() * 1000d) / 1000d  + "\n");
			}
			
			printwriter.close();

		}
		
	
	}

}
