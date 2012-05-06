package test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import back.*;

public class PositionTest {

	Position p;

	Date date;
	
	@Before
	public void setUp() throws Exception {
		date = new Date();
		double lat = 10.5, lon = 5.9, atitude = 3.9;
		p = new Position(date, lat, lon, atitude);
	}
	
	@Test
	public void PositionDoubles(){		
		
		assertTrue(p.getLat() == 10.5);
		assertTrue(p.getLon() == 5.9);
		assertTrue(p.getAltitude() == 3.9);
	}
	
	@Test
	public void PositionTime(){
		String s1 = p.getTime().toString();
		String s2 = date.toString();
		
		assertTrue(s1.equals(s2));
	}

}
