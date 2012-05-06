package test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import back.*;

public class PositionStorageTest {

	PositionStorage storage;
	Random random;
	
	Date lastDate;
	double lastLat, lastLon, lastAltitude;
	
	final int  defaultLat = 0;
	final int  defaultLon = 0;
	final int  defaulAltitude = 0;
	final long defaultTime = 0;
	
	
	public PositionStorageTest(){
		storage = new PositionStorage();
		random = new Random();
		lastDate = new Date(defaultTime);
		lastLat = defaultLat;
		lastLon = defaultLon;
		lastAltitude = defaulAltitude;
	}
	
	protected Position generatePosition(){
		lastDate.setTime(lastDate.getTime() + 1);
		lastLat++;
		lastLon++;
		lastAltitude++;
		Date date = (Date)lastDate.clone();
		return new Position(date, lastLat, lastLon, lastAltitude);
	}
	
	@Test
	public void emptyStorage(){
		
		boolean exceptionThrown = false;
		try {
			storage.getLastPosition();
		} catch (NoSuchFieldException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		
		List<Position> list = storage.getAll();
		assertTrue(list.isEmpty());
		
		exceptionThrown = false;
		try {
			storage.getLastPositions(0);
		} catch (IllegalArgumentException e) {
			exceptionThrown = true;
		} catch (NoSuchFieldException e) {
			fail("this exception mustn't be raised");
		}
		assertTrue(exceptionThrown);
		
		exceptionThrown = false;
		try {
			storage.getLastPositions(2);
		} catch (IllegalArgumentException e) {
			fail("this exception mustn't be raised");
		} catch (NoSuchFieldException e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}
	
	
	protected void create5Positions(){
		for(int i=0; i<5; i++){
			storage.addPosition(this.generatePosition());
		}
	}
	
	@Test
	public void getLastTest(){
		create5Positions();
		
		assertFalse(storage.getAll().isEmpty());
		
		try {
			Position p = storage.getLastPosition();
			assertTrue(p.getLat() == defaultLat + 5);
			assertTrue(p.getLon() == defaultLon + 5);
			assertTrue(p.getAltitude() == defaulAltitude + 5);
			
			Date after = new Date(defaultTime + 5);
			assertTrue(p.getTime().equals(after));
			
		} catch (NoSuchFieldException e) {
			fail("there must be some position");
		}
	}
	
	@Test
	public void getAllTest(){
		create5Positions();
		
		List<Position> l = storage.getAll();
		assertTrue(l.size() == 5);
		
		int i=0;
		Date after = new Date(defaultTime);
		
		for(Position p : l){
			i++;
			assertTrue(p.getLat() == i);
			assertTrue(p.getLon() == i);
			assertTrue(p.getAltitude() == i);
			
			after.setTime(i);
			assertTrue(p.getTime().equals(after));
		}
	}
	
	@Test
	public void getLast3Test(){
		create5Positions();
		
		List<Position> subset, forceSubset;
		try {
			subset = storage.getLastPositions(3);
			forceSubset = storage.getAll().subList(2, 5);
			
			assertTrue(subset.size() == 3);
			
			int i=0;
			
			for(Position p : subset){
				assertTrue(p.getLat() == (double)(i+3));
				assertTrue(p.getLat() == forceSubset.get(i).getLat());
				assertTrue(p.getLon() == forceSubset.get(i).getLon());
				assertTrue(p.getAltitude() == forceSubset.get(i).getAltitude());
				assertTrue(p.getTime().equals(forceSubset.get(i).getTime()));
				i++;
			}
		} catch (IllegalArgumentException e) {
			fail("argument must be ok");
		} catch (NoSuchFieldException e) {
			fail("there must be some items");
		}
	}	

	@Test
	public void getLast7Test(){
		create5Positions();
		
		List<Position> subset, forceSubset;
		try {
			subset = storage.getLastPositions(7);
			forceSubset = storage.getAll().subList(0, 5);
			
			assertTrue(subset.size() == 5);
			
			int i=0;
			
			for(Position p : subset){
				assertTrue(p.getLat() == (double)(i+1));
				assertTrue(p.getLat() == forceSubset.get(i).getLat());
				assertTrue(p.getLon() == forceSubset.get(i).getLon());
				assertTrue(p.getAltitude() == forceSubset.get(i).getAltitude());
				assertTrue(p.getTime().equals(forceSubset.get(i).getTime()));
				i++;
			}
		} catch (IllegalArgumentException e) {
			fail("argument must be ok");
		} catch (NoSuchFieldException e) {
			fail("there must be some items");
		}
	}	
	

}
