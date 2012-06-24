package at.dornbirn;

import java.util.Observable;
import java.util.Observer;

import back.PositionStorage;
import back.Statistics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Activity for statistics
 * @author fafin
 *
 */
public class StatisticsActivity extends Activity implements Observer{

	protected Statistics statistics;
	
	protected TextView totalDistance, totalTime, totalSpeed, actualSpeed;
	
	protected boolean created = false;
	
	private String TAG = "StatisticsActivity";
	
	protected Handler handler = new Handler();
	
    /**
     * Observe global position storage
     */
    protected void observeStatistics(){
    	statistics = ((LostApplication) this.getApplication()).getStatistics();
    	statistics.addObserver(this);
    }
	
	public void onCreate(Bundle state){
		super.onCreate(state);
		
		observeStatistics();
		
		this.setContentView(R.layout.statistics);
		
		reset();
		
		created = true;
	}
	
	public void reset(){
		totalDistance = (TextView) this.findViewById(R.id.statistics_total_distance);
		totalDistance.setText("x");
		
		totalTime = (TextView) this.findViewById(R.id.statistics_total_time);
		totalTime.setText("x");
		
		totalSpeed = (TextView) this.findViewById(R.id.statistics_total_speed);
		totalSpeed.setText("x");
		
		actualSpeed = (TextView) this.findViewById(R.id.statistics_actual_apeed);
		actualSpeed.setText("x");
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
    			return true;
    		case R.id.itemCurrentPlan:
    			startActivity(new Intent(this, LostActivity.class));
    			return true;
    		case R.id.itemSettings:
    			startActivity(new Intent(this, SettingsActivity.class));
    			return true;
    		case R.id.itemMaps:
    			startActivity(new Intent(this, MapListActivity.class));
    	}
    
    	return super.onOptionsItemSelected(item);
    }

    /**
     * Formats time given in hours to String representation
     * @param hours
     * @return
     */
    public static String formatTime(double hours){
		int totalHours = (int)(hours);
		int totalMinutes = (int)((hours * Statistics.MINUTE) % Statistics.MINUTE);
		
		String totalTimeString = "";
		if(totalHours < 10)
			totalTimeString += "0";
		totalTimeString += totalHours;
		totalTimeString += ":";
		
		if(totalMinutes < 10)
			totalTimeString += "0";
		totalTimeString += totalMinutes;
		
		return totalTimeString;
    }
    
    /**
     * Updates data in user interface
     */
    protected Runnable updateUI = new Runnable() {
        public void run() {
			Log.d(TAG, "updating");
			
			totalDistance.setText(String.format("%.2f", statistics.getTotalDistance()));
			totalTime.setText(formatTime(statistics.getTotalTime()));
			totalSpeed.setText(String.format("%.2f", statistics.getTotalSpeed()));
			actualSpeed.setText(String.format("%.2f", statistics.getActualSpeed()));

        }
    };

	@Override
	public void update(Observable arg0, Object arg1) {
		if(!created)
			return;
		handler.post(updateUI);
	}
	
}
