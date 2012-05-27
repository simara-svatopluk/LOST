package at.dornbirn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Activity for statistics
 * @author fafin
 *
 */
public class StatisticsActivity extends Activity {

	public void onCreate(Bundle state){
		super.onCreate(state);
		
		this.setContentView(R.layout.statistics);
		
		TextView totalDistance = (TextView) this.findViewById(R.id.statistics_total_distance);
		totalDistance.setText("10");
		
		TextView totalTime = (TextView) this.findViewById(R.id.statistics_total_time);
		totalTime.setText("1.2");
		
		TextView totalSpeed = (TextView) this.findViewById(R.id.statistics_total_speed);
		totalSpeed.setText("4.8");
		
		TextView actualSpeed = (TextView) this.findViewById(R.id.statistics_actual_apeed);
		actualSpeed.setText("62.3");
		
		
		
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
    		case R.id.itemNewPlan:	    		
    			startActivity(new Intent(this, PlanActivity.class));
    			return true;
    		case R.id.itemCurrentPlan:
    			startActivity(new Intent(this, LostActivity.class));
    			return true;
    		case R.id.itemSettings:
    			startActivity(new Intent(this, SettingsActivity.class));
    			return true;
    		case R.id.itemDownload:
    			startActivity(new Intent(this, DownloadMapActivity.class));
    	}
    
    	return super.onOptionsItemSelected(item);
    }
	
}
