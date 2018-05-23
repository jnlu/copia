package copia;

import java.io.*;
import java.text.ParseException;
import java.util.*;

public class Main {
		
	public static void match(ArrayList<String[]> customers, ArrayList<String[]> recipients, String output) throws FileNotFoundException, IOException, ParseException {

		HashMap<String, Integer> r_pop = new HashMap<String, Integer>();
		HashMap<String, ArrayList<Map.Entry<String, Double>>> c_matches = new HashMap<String, ArrayList<Map.Entry<String, Double>>>();
		
		for (int i = 0; i < recipients.size(); i++) {
			String[] r = recipients.get(i);
			String r_key = String.join(",", r);
			r_pop.put(String.join(",", r_key), 0);
			for (int j = 0; j < customers.size(); j++) {
				String[] c = customers.get(j);
				String c_key = String.join(",", c);
				if (c_matches.get(c_key) == null) {
					c_matches.put(c_key, new ArrayList<Map.Entry<String, Double>>());
				}
				double comp = Matcher.checkCompatibility(c, r);
				if (comp >= 0) {
					java.util.Map.Entry<String,Double> tup = new java.util.AbstractMap.SimpleEntry<>(r_key,comp);
					r_pop.put(r_key, r_pop.get(r_key) + 1);
					c_matches.get(c_key).add(tup);
				}
			}
		}
		
		for (String key : c_matches.keySet()) {
			String[] c_split = key.split(",");
			String customer = c_split[0] + "_" + c_split[1];
			String filename = output + customer;
			FileWriter filewriter = new FileWriter(new File(filename));
			PrintWriter printwriter = new PrintWriter(filewriter);
			printwriter.print(String.join(",", customer) + "\n\n");
			
			ArrayList<Map.Entry<String, Double>> matches = c_matches.get(key);

			Collections.sort(matches, new Comparator<Map.Entry<String, Double>>() {
			    public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b) {
			        int diff = r_pop.get(a.getKey()) - r_pop.get(b.getKey());
			        if (diff != 0) {
			        	return diff;
			        }
			        else {
			        	return (int) (a.getValue() - b.getValue());
			        }
			    }
			});
			
			for (int i = 0; i < matches.size(); i++) {
				printwriter.print(matches.get(i).getKey() + "\n");
			}
			
			printwriter.close();

		}
	}

}
