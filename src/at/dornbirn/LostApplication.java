package at.dornbirn;

import android.app.Application;
import android.util.Log;
import back.*;

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
