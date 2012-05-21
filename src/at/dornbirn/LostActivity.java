package at.dornbirn;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LostActivity extends MapActivity {
	MapView mapView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        /*
         * TODO find out what's causing problem in your emulator(s)
         */
        startService(new Intent(this, PositionUpdater.class)); // start positioning service
        
        Button toStatistics = (Button) this.findViewById(R.id.main_to_statistics);
        
        
        toStatistics.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				LostActivity.this.startActivity(new Intent(LostActivity.this, StatisticsActivity.class));
			}
        });
        
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
    
    
    

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
    
    
}