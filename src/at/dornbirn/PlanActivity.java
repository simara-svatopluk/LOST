package at.dornbirn;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.maps.MapActivity;

public class PlanActivity extends MapActivity {

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
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
	    			return true;
	    		case R.id.itemCurrentPlan:
	    			startActivity(new Intent(this, LostActivity.class));
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
