package at.dornbirn;

import java.io.File;
import java.util.List;
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
import android.widget.ToggleButton;

public class LostActivity extends MapActivity implements Observer{
	
	MapView mapView;
	Position lastPosition = null;
	ArrayWayOverlay overlay;
	
	boolean locked = false;
	
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
        paint.setStrokeWidth(6);
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
     * creates path by existing data from position storage
     */
    protected void createExistingPath(){
    	PositionStorage positionStorage = ((LostApplication) this.getApplication()).getPositionStorage();
    	List<Position> positions = positionStorage.getAll();
    	if(positions.size() > 1){
    		GeoPoint[][] points = new GeoPoint[1][positions.size()];
    		int counter = 0;
    		for(Position p : positions){
    			points[0][counter] = new GeoPoint(p.getLat(), p.getLon());
    			counter++;
    		}
            OverlayWay way = new OverlayWay(points);
            overlay.addWay(way);
        	try {
    			lastPosition = positionStorage.getLastPosition();
    		} catch (NoSuchFieldException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    }
    
    /**
     * Creates overlay on mapView
     */
    protected void createOverlay(){
        Paint outline = lineColor(Color.MAGENTA);
        overlay = new ArrayWayOverlay(null, outline);
        mapView.getOverlays().add(overlay);
        createExistingPath();
    }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.map_view);
        
        mapView = (MapView) findViewById(R.id.mapview);
        
        mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);

        Bundle extra = getIntent().getExtras();
        if(extra == null){;
        	extra = new Bundle();
        	mapView.setMapFile(new File("/sdcard/maps/austria.map"));
        }
        else{
    		String focusedMap = extra.getString("map");
    		mapView.setMapFile(new File("/sdcard/maps/" + focusedMap));
        }
        	
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
		
		if(locked)
		{
			mapView.setCenter(new GeoPoint(lastPosition.getLat(), lastPosition.getLon()));
		}
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
    		case R.id.itemMaps:
    			startActivity(new Intent(this, MapListActivity.class));
    		
    	}
    
    	return super.onOptionsItemSelected(item);
    }
    
    
    public void bLockClicked(View view)
    {
    	ToggleButton b = (ToggleButton) view;
    	
    	if(b.isChecked())
    	{
    		locked = true;
    		mapView.setClickable(false);
    		
    	}
    	else 
    	{
    		locked = false;
    		mapView.setClickable(true);
    		
    		if(lastPosition != null)
    		{
    			mapView.setCenter(new GeoPoint(lastPosition.getLat(), lastPosition.getLon()));
    		}
    	}
    	
    	
    	
    }
    
    public void bGpsSwitchClicked(View view)
    {
    	ToggleButton b = (ToggleButton) view;
    	
    	if(b.isChecked())
    	{
    	
    		startService(new Intent(this, PositionUpdater.class));
    	}
    	
    	else
    	{
    		stopService(new Intent(this, PositionUpdater.class));    	
    	}
    	
    	
    }
    
    
}
