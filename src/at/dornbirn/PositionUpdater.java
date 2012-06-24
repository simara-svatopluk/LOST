package at.dornbirn;

import java.util.*;

import back.*;
import android.location.*;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import android.app.*;
import android.content.*;




/**
 * Service responsible for calculating most probable actual position
 * and updating PositionStorage
 * @author oddspider
 *
 */
public class PositionUpdater extends Service implements LocationListener
{
	private static final String TAG = PositionUpdater.class.getSimpleName();
	
	private PositionStorage positionStorage;
	
	private LocationManager lm;
	 
	// temporary positions for correction (averaging them)
	private Position[] tempPos = new Position[3];
	private int corrLoaded = 0; 
	
	
	// called only once, first time service is started
	public void onCreate()
	{
		super.onCreate();		
		Log.d(TAG, "On create");
		
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);	
		
		positionStorage = ((LostApplication) this.getApplication()).getPositionStorage();
		
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, this);
		
		// !!
		//lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, this);
		
		
		Location last = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);		
		if(last != null)
		{
			//positionStorage.addPosition( new Position(new Date(), last.getLatitude(), last.getLongitude(), last.getAltitude()));
		}
			    
			
	}
	
	// called every time service is started
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Log.d(TAG, "On start command");
		
	//	lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
		
		
		return START_STICKY;
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		
		lm.removeUpdates(this);
		
		Log.d(TAG, "On destroy");
	}
	
	
	
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
		
		tempPos[corrLoaded] = new Position(new Date(), location.getLatitude(), location.getLongitude(), location.getAltitude());
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
		
		
		
		
		
		
		Log.d(TAG, "Location changed");

	}

	public void onProviderDisabled(String provider) {
		Log.d(TAG, "Provider Disabled");
	}

	public void onProviderEnabled(String provider) {
		Log.d(TAG, "Provider Enabled");

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.d(TAG, "Status changed");

	}


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	

	

}
