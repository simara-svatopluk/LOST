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
	
	
	
	// called only once, first time service is started
	public void onCreate()
	{
		super.onCreate();		
		Log.d(TAG, "On create");
		
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);	
		
		positionStorage = ((LostApplication) this.getApplication()).getPositionStorage();
			    
		
		
	
	}
	
	// called every time service is started
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Log.d(TAG, "On start command");
		
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		
		return START_STICKY;
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		Log.d(TAG, "On destroy");
	}
	
	
	
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
		Position newPos = new Position(new Date(), location.getLatitude(), location.getLongitude(), location.getAltitude());
		
		positionStorage.addPosition(newPos);
		
		
		
		
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
