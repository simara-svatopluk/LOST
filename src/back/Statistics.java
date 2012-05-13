package back;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.location.Location;

/**
 * Statistic calculation using PositionStorage
 * @author fafin
 *
 */
public class Statistics implements Observer
{
	/**
	 * When we are using Location.distanceBetween,
	 * result has exactly 3 items
	 */
	private final static int DISTANCE_RESULT_SIZE = 3;
	
	/**
	 * Count of points from which is calculated
	 * actual information
	 */
	private final static int ACTUAL_POINTS_COUNT = 10;
	
	/**
	 * Total distance during trip in kilometers (m)
	 */
	protected double totalDistance;
	
	/**
	 * Total time during trip in seconds (s)
	 */
	protected double totalTime;
	
	/**
	 * Actual speed
	 */
	protected double actualSpeed;
	
	
	public double getTotalDistance() {
		return totalDistance;
	}

	public double getTotalTime() {
		return totalTime;
	}
	
	public double getTotalSpeed(){
		if(totalTime == 0)
			return 0;
		
		return totalDistance/totalTime;
	}
	
	public double getActualSpeed(){
		return actualSpeed;
	}

	/**
	 * last known position - before actualization
	 */
	protected Position lastPosition;
	
	public Statistics(){
		totalDistance = 0;
		totalTime = 0;
		actualSpeed = 0;
		lastPosition = null;
	}
	
	/**
	 * Calculates distance between 2 positions, in m
	 * @param p1
	 * @param p2
	 * @return
	 */
	protected static double distance(Position p1, Position p2){
		
		float[] results = new float[DISTANCE_RESULT_SIZE];
		try{
			Location.distanceBetween(p1.getLat(), 
									 p1.getLon(), 
									 p2.getLat(), 
									 p2.getLon(), 
									 results);
		}catch(IllegalArgumentException e){
			return 0;
		}
		
		return results[0];
	}
	
	/**
	 * Calculates time difference between 2 positions, in s
	 * @param p1
	 * @param p2
	 * @return
	 */
	protected static double timeDiff(Position p1, Position p2){
		return p1.getTime().compareTo(p2.getTime());
	}
	
	
	/**
	 * distance from last position to a new
	 * @param newPosition
	 * @return
	 */
	protected double distanceToNew(Position newPosition){
		return distance(lastPosition, newPosition);
	}
	
	/**
	 * time from last position in seconds
	 * @param newPosition
	 * @return
	 */
	protected double timeToNew(Position newPosition){
		return timeDiff(lastPosition, newPosition);
	}
	
	/**
	 * Updates actual speed
	 * @throws NoSuchFieldException 
	 * @throws IllegalArgumentException 
	 */
	protected void updateActualSpeed(PositionStorage storage){
		try {
			List<Position> lastPositions = storage.getLastPositions(ACTUAL_POINTS_COUNT);
			
			double actualDistance = 0;
			double actualTime = 0;
			
			Position lastOne = null;
			
			for(Position p : lastPositions){
				if(lastOne == null){
					lastOne = p;
					continue;
				}
				actualDistance += distance(lastOne, p);
				actualTime += timeDiff(lastOne, p);
			}
			
			if(actualTime == 0){
				actualSpeed = 0;
			}else{
				actualSpeed = actualDistance/actualTime;
			}
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	public void update(Observable storage, Object data){
		Position position = (Position)data;
		
		if(lastPosition == null){
			lastPosition = position;
		}
		
		totalDistance += distanceToNew(position);
		totalTime += timeToNew(position);
		updateActualSpeed((PositionStorage) storage);
	}
	
	
	
	

}
