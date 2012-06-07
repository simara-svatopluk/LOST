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
		positionStorage.addPosition(p);
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
