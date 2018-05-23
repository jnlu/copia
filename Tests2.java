package copia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class Tests2 {

	@Test
	private void testDistanceBase() {
		
		//Base
		assert(Utils.checkDistance("0", "0", "0", "0") == 0);
		assert(Utils.checkDistance("0", "0", "100", "100") > 10);
	}
	
	@Test
	private void testDistance() {

		//Closer to edges
		assert(Utils.checkDistance("37.730494", "-122.371925", "37.809052", "-122.483365") < 10);
		assert(Utils.checkDistance("37.730494", "-122.371925", "37.731712", "-122.451843") < 10);
		assert(Utils.checkDistance("37.730494", "-122.371925", "37.854427", "-122.478688") > 10);

	}
	
	@Test
	private void testCategoriesBase() {
		
		//000000
		//000000
		assert(Utils.checkCategories("0", "0"));
		
		//000001
		//000000
		assert(Utils.checkCategories("1", "0"));
		
		//000001
		//000001
		assert(!(Utils.checkCategories("1", "1")));
		
		//000000
		//111111
		assert(Utils.checkCategories("0", "63"));
		
		//000001
		//111111
		assert(!(Utils.checkCategories("1", "63")));
		
		//111111
		//000001
		assert(!(Utils.checkCategories("63", "1")));
		
		//111110
		//000001
		assert(Utils.checkCategories("62", "1"));
		

	}
	
	@Test
	private void testCategories() {
	
		//101011
		//010100
		assert(Utils.checkCategories("43", "20"));
		
		//001000
		//001010
		assert(!(Utils.checkCategories("8", "10")));
		

	}
	
	@Test
	private void testTimesBase() throws ParseException {
		
		String[] r = {null, null, null, null, null, null, null, null, null, null, null, null, "65535", "65534", "32768", "0", "0", "0", "0"};
		
		//Schedule availability, bit to check
		
		//1111111111111111
		//0
		assert(Utils.checkTime("2016-11-27T08:00:00-08:00", r));

		//1111111111111110
		//0
		assert(!(Utils.checkTime("2016-11-28T08:00:00-08:00", r)));
		
		//1000000000000000
		//15
		assert(Utils.checkTime("2016-11-29T23:00:00-08:00", r));
		
		//0000000000000000
		//7
		assert(!(Utils.checkTime("2016-11-30T15:00:00-08:00", r)));

	}
	
	@Test
	private void testTimes() throws ParseException {
		
		String[] r = {null, null, null, null, null, null, null, null, null, null, null, null, "32251", "29178", "32768", "0", "0", "0", "0"};

		//Schedule availability, bit to check
		//0111110111111011, 2
		assert(!(Utils.checkTime("2016-11-20T10:00:00-08:00", r)));
		
		//0111000111111010, 3
		assert(Utils.checkTime("2016-11-21T11:00:00-08:00", r));
		
	}
	
	@Test
	private void testOverallComatibilityp() throws ParseException {
		
		String customer = "Brett,Sullivan,2784 Ella Street,San Francisco,CA,94107,US,BrettJSullivan@teleworm.us,650-262-4366,37.728912,-122.324225,45,2016-11-29T16:00:00-08:00,America/Los_Angeles";

		// Should pass and return a distance of 0
		String recipient1 = "Brett,Sullivan,2784 Ella Street,San Francisco,CA,94107,US,BrettJSullivan@teleworm.us,650-262-4366,37.728912,-122.324225,0,65535,65535,65535,65535,65535,65535,65535";
		
		// All of the following recipients are the same as the first except for one attribute
		
		// Should fail because of distance
		String recipient2 = "Brett,Sullivan,2784 Ella Street,San Francisco,CA,94107,US,BrettJSullivan@teleworm.us,650-262-4366,137.728912,-122.324225,0,65535,65535,65535,65535,65535,65535,65535";
		
		// Should fail because of categories
		String recipient3 = "Brett,Sullivan,2784 Ella Street,San Francisco,CA,94107,US,BrettJSullivan@teleworm.us,650-262-4366,37.728912,-122.324225,1,65535,65535,65535,65535,65535,65535,65535";
		
		// Should fail because of pickuptime
		String recipient4 = "Brett,Sullivan,2784 Ella Street,San Francisco,CA,94107,US,BrettJSullivan@teleworm.us,650-262-4366,37.728912,-122.324225,0,0,0,0,0,0,0,0";

		String[] c = customer.split(",");
		String[] r1 = recipient1.split(",");
		String[] r2 = recipient2.split(",");
		String[] r3 = recipient3.split(",");
		String[] r4 = recipient4.split(",");

		assert(Utils.checkCompatibility(c, r1) == 0);
		assert(Utils.checkCompatibility(c, r2) == -1);
		assert(Utils.checkCompatibility(c, r3) == -1);
		assert(Utils.checkCompatibility(c, r4) == -1);

	}
	

}
