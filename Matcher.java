package copia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Matcher {

	public static double checkCompatibility(String[] customer, String[] recipient) throws ParseException {
		
		double distance = checkDistance(customer[9], customer[10], recipient[9], recipient[10]);
		
		if (distance >= 10) {
			return -1;
		}
		
		if (!(checkCategories(customer[11], recipient[11]))) {
			return -1;
		}

		if (!(checkTime(customer[12], recipient))) {
			return -1;
		}
		
		return distance;
	}
	
	public static boolean checkCategories(String c, String r) {

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
		
		// Uses the haversine formula to calculate the distance:
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
		

		String date = ctime.split("T")[0];
		String time = ctime.split("T")[1];
		Calendar c = Calendar.getInstance();
		c.setTime(new SimpleDateFormat("yyyy-M-dd").parse(date));
		String schedule = String.format("%016d", Long.parseLong(Integer.toBinaryString(Integer.parseInt(rdays[c.get(Calendar.DAY_OF_WEEK) + 11]))));
		int start = Integer.parseInt(time.substring(0, 2));
		
		if (schedule.charAt(23 - start) == '1') {
			return true;
		}
		return false;
	}
	
}
