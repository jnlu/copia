package copia;

import java.text.ParseException;
import org.junit.jupiter.api.Test;

class Tests {

	@Test
	public void testDistanceBase() {
		
		String c_lat = "37.728912";
		String c_lon = "-122.324225";
		
		//Base
		assert(Matcher.checkDistance(c_lat, c_lon, c_lat, c_lon) == 0);
		assert(Matcher.checkDistance(c_lat, c_lon, "100", "100") > 10);
	}
	
	@Test
	public void testDistance() {

		//Closer to edges
		assert(Matcher.checkDistance("37.730494", "-122.371925", "37.809052", "-122.483365") < 10);
		assert(Matcher.checkDistance("37.730494", "-122.371925", "37.731712", "-122.451843") < 10);
		assert(Matcher.checkDistance("37.730494", "-122.371925", "37.854427", "-122.478688") > 10);

	}
	
	@Test
	public void testCategoriesBase() {
		
		//000000
		//000000
		assert(Matcher.checkCategories("0", "0"));
		
		//000001
		//000000
		assert(Matcher.checkCategories("1", "0"));
		
		//000010
		//000000
		assert(Matcher.checkCategories("2", "0"));
		
		//000010
		//111111
		assert(Matcher.checkCategories("63", "0"));
		
		//000001
		//111111
		assert(!(Matcher.checkCategories("1", "63")));
		
		//000000
		//111111
		assert(Matcher.checkCategories("0", "63"));

	}
	
	@Test
	public void testCategories() {
		
		//111110
		//000001
		assert(Matcher.checkCategories("62", "1"));
	
		//101011
		//010100
		assert(Matcher.checkCategories("43", "20"));
		
		//001000
		//001010
		assert(!(Matcher.checkCategories("8", "10")));

	}
	
	@Test
	public void testTimesBase() throws ParseException {
		
		String[] r = {null, null, null, null, null, null, null, null, null, null, null, null, "65535", "65534", "32768", "0", "0", "0", "0"};
		
		//Schedule availability, bit to check
		//1111111111111111, 0
		assert(Matcher.checkTime("2016-11-27T08:00:00-08:00", r));

		//1111111111111110, 0
		assert(!(Matcher.checkTime("2016-11-28T08:00:00-08:00", r)));
		
		//1000000000000000, 15
		assert(Matcher.checkTime("2016-11-29T23:00:00-08:00", r));
		
		//0000000000000000, 7
		assert(!(Matcher.checkTime("2016-11-30T15:00:00-08:00", r)));

	}
	
	@Test
	public void testTimes() throws ParseException {
		
		String[] r = {null, null, null, null, null, null, null, null, null, null, null, null, "32251", "29178", "32768", "0", "0", "0", "0"};

		//Schedule availability, bit to check
		//0111110111111011, 2
		assert(!(Matcher.checkTime("2016-11-20T10:00:00-08:00", r)));
		
		//0111000111111010, 3
		assert(Matcher.checkTime("2016-11-21T11:00:00-08:00", r));
		
	}

}
