package copia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Printer {
	
	public static void print(String output, HashMap<String, Integer> pop, HashMap<String, ArrayList<Map.Entry<String, Double>>> matches) throws IOException {
		
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
			
			String filename = output + customer + ".csv";
			names.add(customer);
			FileWriter filewriter = new FileWriter(new File(filename));
			PrintWriter printwriter = new PrintWriter(filewriter);
			
			// Same header as the recipients CSV but with popularity and distance added at the end
			// Popularity is how many total customers each recipient is compatible with, and distance is how far away the customer is from the recipient
			printwriter.print("FirstName,LastName,Street,City,State,Postal,Country,Email,Phone,Latitude,Longitude,Restrictions,Sunday,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Popularity,Distance\n");
			
			// Sorts the array based on popularity, then distance
			ArrayList<Map.Entry<String, Double>> curr_matches = Matcher.sort(key, pop, matches);
			
			// Writes the recipients
			for (int i = 0; i < curr_matches.size(); i++) {
				String k = curr_matches.get(i).getKey();
				printwriter.print(k + "," + pop.get(k) + "," + (double)Math.round(curr_matches.get(i).getValue() * 1000d) / 1000d  + "\n");
			}
			
			printwriter.close();

		}
	}

}
