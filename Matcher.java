package copia;

import java.text.ParseException;
import java.util.*;

public class Matcher {
		
	public static Map.Entry<HashMap<String, Integer>, HashMap<String, ArrayList<Map.Entry<String, Double>>>> match(ArrayList<String[]> customers, ArrayList<String[]> recipients) throws ParseException {

		// r_pop will keep a count of how many recipients each customer is compatible with
		HashMap<String, Integer> r_pop = new HashMap<String, Integer>();
		// c_matches will keep a list of the customers each recipient is compatible with and their distance from the customer
		HashMap<String, ArrayList<Map.Entry<String, Double>>> c_matches = new HashMap<String, ArrayList<Map.Entry<String, Double>>>();
		
		int r_size = recipients.size();
		int c_size = customers.size();
		for (int i = 0; i < r_size; i++) {
			
			String[] r = recipients.get(i);
			String r_key = String.join(",", r);
			r_pop.put(String.join(",", r_key), 0);
			for (int j = 0; j < c_size; j++) {
				String[] c = customers.get(j);
				String c_key = String.join(",", c);
				if (i == 0) {
					// Sets up each customer's arraylist
					c_matches.put(c_key, new ArrayList<Map.Entry<String, Double>>());
				}
				// Determines if it's compatible. Value is the distance if they are, -1 otherwise
				double comp = Utils.checkCompatibility(c, r);
				if (comp >= 0) {
					// If they're compatible, the recipient's information and the distance is added to the customer's list
					// Also increments the recipient's popularity count
					java.util.Map.Entry<String,Double> tup = new java.util.AbstractMap.SimpleEntry<>(r_key,comp);
					r_pop.put(r_key, r_pop.get(r_key) + 1);
					c_matches.get(c_key).add(tup);
				}
			}
		}
		
		// Returns the popularity and match maps
		
		return new java.util.AbstractMap.SimpleEntry<>(r_pop, c_matches);
				
	}
	
	public static ArrayList<Map.Entry<String, Double>> sort(String customer, HashMap<String, Integer> pop, HashMap<String, ArrayList<Map.Entry<String, Double>>> matches) {
		
		//Given a customer string key, fetches its corresponding list of recipients and sorts it
		
		ArrayList<Map.Entry<String, Double>> curr_matches = matches.get(customer);

		Collections.sort(curr_matches, new Comparator<Map.Entry<String, Double>>() {
		    public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b) {
		    	// First sorts based on popularity; lower popularity takes precedence
		        int diff = pop.get(a.getKey()) - pop.get(b.getKey());
		        if (diff != 0) {
		        	return diff;
		        }
		        // If the popularities are equal, then sorts on distance; shorter distance takes precedence
		        else {
		        	return (int) (a.getValue() - b.getValue());
		        }
		    }
		});
			
		return curr_matches;

	}
		

}
