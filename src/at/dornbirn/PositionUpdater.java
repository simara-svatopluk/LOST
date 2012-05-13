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
 * Class responsible for calculating most probable actual position
 * @author oddspider
 *
 */

public class PositionUpdater extends Service implements LocationListener
{
	private static final String TAG = PositionUpdater.class.getSimpleName();
	
	private PositionStorage positionStorage;
	
	private LocationManager lm;
//	private PositionStorage storage;	
	
	
	
	/*
	public PositionUpdater(LocationManager lm, PositionStorage storage)
	{
	/*	this.storage = storage;
		this.lm = lm;
	    
	    Location lastloc = lm.getLastKnownLocation("network");   	    
	    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);	    
	    int a = 2+2;    	
	}*/
	
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
		
		return START_STICKY;
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		Log.d(TAG, "On destroy");
	}
	
	
	
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
		Position newPos = new Position(null, location.getLatitude(), location.getLongitude(), location.getAltitude());
		
		positionStorage.addPosition(newPos);
		
		
		
		
		Log.d(TAG, "Location changed");

	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	

	

}