package copia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {
	
	// Performs three checks to see if a given customer and recipient are compatible
	// Checks for distance, food categories, and pickup/dropoff time
	// Returns -1 if not compatible, and the distance if they are

	public static double checkCompatibility(String[] customer, String[] recipient) throws ParseException {
		
		double distance = checkDistance(customer[9], customer[10], recipient[9], recipient[10]);
		
		// Must be within 10 miles
		if (distance >= 10) {
			return -1;
		}
		
		else if (!(checkCategories(customer[11], recipient[11]))) {
			return -1;
		}

		else if (!(checkTime(customer[12], recipient))) {
			return -1;
		}
		
		else {
			return distance;
		}
	}
	
	public static boolean checkCategories(String c, String r) {
		
		// Converts both values to binary strings, then checks if their values
		// at any position are both 1. Returns false if there's a match, true otherwise

		String c_cat = String.format("%06d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(c))));
		String r_cat = String.format("%06d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(r))));
		for (int i = 0; i < 6; i++) {
			if ((c_cat.charAt(i) == '1') && (r_cat.charAt(i) == '1')) {
				return false;
			}
		}
		return true;
	}
	
	public static double checkDistance(String clat, String clon, String rlat, String rlon) {
		
		// Uses the Haversine formula to calculate the distance between two points using their latitudes and longitudes
		// https://en.wikipedia.org/wiki/Haversine_formula
		
		double c_lat = Double.parseDouble(clat) * Math.PI / 180;
		double c_lon = Double.parseDouble(clon)* Math.PI / 180;
		
		double r_lat = Double.parseDouble(rlat) * Math.PI / 180;
		double r_lon = Double.parseDouble(rlon) * Math.PI / 180;
		
		double a = Math.pow(Math.sin((r_lat - c_lat) / 2), 2) + (Math.cos(c_lat) * Math.cos(r_lat) * Math.pow((Math.sin((r_lon - c_lon) / 2)), 2));
		double distance = 2 * 3961 * Math.asin(Math.sqrt(a));
		
		return distance;
	}
	
	public static boolean checkTime(String ctime, String[] rdays) throws ParseException {
		
		// Determines which day the customer's food is available for pickup, then checks to see
		// if the recipient is free for that hour.
		
		String date = ctime.split("T")[0];
		String time = ctime.split("T")[1];
		
		// Determines which day of the week the day falls on
		Calendar c = Calendar.getInstance();
		c.setTime(new SimpleDateFormat("yyyy-M-dd").parse(date));
		
		// Using the day of the week, finds the corresponding schedule value for the customer, then copverts it to a binary string
		String schedule = String.format("%016d", Long.parseLong(Integer.toBinaryString(Integer.parseInt(rdays[c.get(Calendar.DAY_OF_WEEK) + 11]))));
		
		// Checks to see if the value at the respective time is 1. If so, returns true, otherwise returns false
		
		int start = Integer.parseInt(time.substring(0, 2));
		if (schedule.charAt(23 - start) == '1') {
			return true;
		}
		return false;
	}
	
}