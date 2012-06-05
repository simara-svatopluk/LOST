package at.dornbirn;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import org.mapsforge.android.maps.MapActivity;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.overlay.ArrayWayOverlay;
import org.mapsforge.android.maps.overlay.OverlayWay;
import org.mapsforge.core.GeoPoint;

import back.DisconnectedPositionUpdater;
import back.Position;
import back.PositionStorage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.*;

public class LostActivity extends MapActivity implements Observer{
	
	MapView mapView;
	Position lastPosition = null;
	ArrayWayOverlay overlay;
	
	private static final String TAG = LostActivity.class.getSimpleName();
	
	/**
	 * Returns color prepared for painting
	 * @param color
	 * @return
	 */
    public static Paint lineColor(int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setAlpha(160);
        paint.setStrokeWidth(4);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setStrokeJoin(Paint.Join.ROUND);
        return paint;
    }
    
    /**
     * Adds line on the map
     * @param one
     * @param two
     */
    protected void addLine(GeoPoint one, GeoPoint two){
        GeoPoint[][] points = new GeoPoint[1][2];
        points[0][0] = one;
        points[0][1] = two;        
        OverlayWay way = new OverlayWay(points);
        overlay.addWay(way);
    }
	
    /**
     * Observe global position storage
     */
    protected void observePositionStorage(){
        PositionStorage positionStorage = ((LostApplication) this.getApplication()).getPositionStorage();
        positionStorage.addObserver(this);
    }
    
    /**
     * Creates overlay on mapView
     */
    protected void createOverlay(){
        Paint outline = lineColor(Color.MAGENTA);
        overlay = new ArrayWayOverlay(null, outline);
        mapView.getOverlays().add(overlay);
    }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapView = new MapView(this);
        mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);
        mapView.setMapFile(new File("/sdcard/maps/andorra.map"));
        
        setContentView(mapView);
        
        createOverlay();
        
        observePositionStorage();
        
        /*
         * TODO find out what's causing problem in your emulator(s)
         */
        //startService(new Intent(this, PositionUpdater.class)); // start positioning service
        /*
         * OR turn On DisconnectedPositionUpdater 
         */
        startService(new Intent(this, DisconnectedPositionUpdater.class));

        
    }

	@Override
	public void update(Observable observable, Object data) {
		Position p = (Position) data;
		if(lastPosition != null){
			addLine(new GeoPoint(lastPosition.getLat(), lastPosition.getLon()), new GeoPoint(p.getLat(), p.getLon()));
		}
		lastPosition = p;
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
    		case R.id.itemDownload:
    			startActivity(new Intent(this, MapListActivity.class));
    		
    	}
    
    	return super.onOptionsItemSelected(item);
    }
    
    
}