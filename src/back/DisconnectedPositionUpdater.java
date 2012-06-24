package back;

import java.util.Date;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import at.dornbirn.LostApplication;

/**
 * Provides new position in testing environment
 * Updates PositionStorage
 * @author fafin
 *
 */
public class DisconnectedPositionUpdater extends Service{
	
	/**
	 * Simple timer in a separate thread
	 * @author fafin
	 *
	 */
	class Timer extends Thread{
		
		protected boolean run;
		protected int delay;
		
		public Timer(int miliseconds){
			run = true;
			delay = miliseconds;
		}
		
		public void run(){
			while(run){
				DisconnectedPositionUpdater.this.generatePosition();
				try {
					sleep(delay);
				} catch (InterruptedException e) { }
			}
		}
		
		public void kill(){
			run = false;
		}
		
	}
	
	private PositionStorage positionStorage;
	
	private Position[] tempPos = new Position[3];
	
	private int corrLoaded = 0;
	
	//default values
	private double lat = 48.202625, lon = 16.393231, alt = 151;
	private int delay = 500;
	
	private int step = 0;
	private static final String TAG = DisconnectedPositionUpdater.class.getSimpleName();
	
	public void onCreate(){
		super.onCreate();
		positionStorage = ((LostApplication) this.getApplication()).getPositionStorage();
		Timer timer = new Timer(delay);
		timer.start();
	}
	
	/**
	 * Generates position
	 * Longitude grows like a linear function
	 * Latitude looks like a sin function with amplitude 1/20°
	 * Altitude looks like a cos function with amplitude 20m
	 */
	protected void generatePosition(){
		step++;
		double x = step/20.0;
		double newLat = lat + Math.sin(x)/20.0;
		double newAlt = alt + Math.cos(x)*20;
		Date newDate = new Date();
		Position p = new Position(newDate, newLat, lon, newAlt);
		//Log.d(TAG, newDate.toString() + " " + newLat + " " + lon + " " + newAlt);
		lon += 0.001;
	//	positionStorage.addPosition(p);
		
		
		
		
		

		tempPos[corrLoaded] = new Position(newDate, newLat, lon, newAlt);
		corrLoaded++;
		
		if(corrLoaded == 3)
		{
			corrLoaded = 0;
			
			double lat = (tempPos[0].getLat() + 
				  tempPos[1].getLat() +
				  tempPos[2].getLat()) / 3;
			
			double lon = (tempPos[0].getLon() + 
					  tempPos[1].getLon() +
					  tempPos[2].getLon()) / 3;
			
			double alt = (tempPos[0].getAltitude() + 
					  tempPos[1].getAltitude() +
					  tempPos[2].getAltitude()) / 3;					
			
			Position newPos = new Position(new Date(), lat, lon, alt);		
			
			positionStorage.addPosition(newPos);		
		
		}
		
		
		
		
		
		
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
