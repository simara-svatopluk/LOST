package at.dornbirn;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
         * TODO start service in another thread
         */
        //startService(new Intent(this, PositionUpdater.class)); // start positioning service
        
        Button toStatistics = (Button) this.findViewById(R.id.main_to_statistics);
        toStatistics.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				LostActivity.this.startActivity(new Intent(LostActivity.this, StatisticsActivity.class));
			}
        });
        
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
    
    
}