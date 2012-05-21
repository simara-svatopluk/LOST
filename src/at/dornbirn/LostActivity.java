package at.dornbirn;

import java.io.File;
import java.util.List;

import org.mapsforge.android.maps.MapActivity;
import org.mapsforge.android.maps.MapController;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.overlay.Overlay;
import org.mapsforge.android.maps.overlay.OverlayItem;
import org.mapsforge.core.GeoPoint;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.*;

public class LostActivity extends MapActivity {
	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawable;
	MyItemizedOverlay itemizedOverlay;
	MapController mc;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapView mapView = new MapView(this);
        mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);
        mapView.setMapFile(new File("/sdcard/maps/albania.map"));
        
        setContentView(mapView);
        
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.marker_red);
        itemizedOverlay = new MyItemizedOverlay(drawable);
        
        GeoPoint geoPoint = new GeoPoint(41.39469, 19.86782);
        OverlayItem overlayitem = new OverlayItem(geoPoint," "," ");
        itemizedOverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedOverlay);
        
        mc = mapView.getController();
        mc.setCenter(geoPoint);
        mc.setZoom(5);
        
        
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