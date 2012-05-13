package at.dornbirn;

import android.app.Application;
import android.util.Log;
import back.*;


// Extends basic application, for purpose of accessing single "PositionStorage"
// from all Activites / Services
public class LostApplication extends Application 
{
	private static final String TAG = LostApplication.class.getSimpleName();
	
	private PositionStorage positionStorage = new PositionStorage();
	
	public PositionStorage getPositionStorage()
	{
		Log.d(TAG, "Position storage requested");
		return positionStorage;
	}

}
