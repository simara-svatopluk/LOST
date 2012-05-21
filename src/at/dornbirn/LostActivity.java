package at.dornbirn;

import java.io.File;
import org.mapsforge.android.maps.MapActivity;
import org.mapsforge.android.maps.MapView;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;

public class LostActivity extends MapActivity {
	MapView mapView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapView mapView = new MapView(this);
        mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);
        mapView.setMapFile(new File("/sdcard/maps/austria.map"));
        
        setContentView(mapView);
        
        /*
         * TODO find out what's causing problem in your emulator(s)
         */
        //startService(new Intent(this, PositionUpdater.class)); // start positioning service

        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	new MenuInflater (this).inflate(R.menu.menu1, menu);
    	
		return super.onCreateOptionsMenu(menu);   
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch (item.getItemId())
    	{
    		case R.id.itemStats:
    			startActivity(new Intent(this, StatisticsActivity.class));
    			return true;
    		case R.id.itemNewPlan:
    			startActivity(new Intent(this, PlanActivity.class));
    			return true;
    		case R.id.itemCurrentPlan:
    			return true;
    		case R.id.itemSettings:
    			startActivity(new Intent(this, SettingsActivity.class));
    			return true;
    	}
    
    	return super.onOptionsItemSelected(item);
    }
    
    
}