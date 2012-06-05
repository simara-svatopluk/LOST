package at.dornbirn;

import java.util.*;
import java.text.*;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import back.*;

public class PosViewActivity extends ListActivity 
{
	private static final String TAG = PosViewActivity.class.getSimpleName();
	private PositionStorage positionStorage;
	
	

	public class PosListAdapter extends ArrayAdapter<Position>
	{
		private List<Position> list;
		private Context context; 
		private int id;
		
		public PosListAdapter(Context context, int id, List<Position> list)
		{
			super(context, id, list);
			
			this.list = list;
			this.context = context;
			this.id = id;
			
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View row = convertView;
			if(row == null)
			{
				LayoutInflater inflater = ((Activity)context).getLayoutInflater();
				row = inflater.inflate(id, parent, false);			
			}
			
			TextView lat = (TextView) row.findViewById(R.id.lat);
			TextView lon = (TextView) row.findViewById(R.id.lon);
			TextView time = (TextView) row.findViewById(R.id.time);
			TextView ldate = (TextView) row.findViewById(R.id.date);
			TextView alt = (TextView) row.findViewById(R.id.alt);
			
			
			DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.MEDIUM);
			DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
			
			Date date = list.get(position).getTime();			
			
			
			lat.setText( "lat: " + String.valueOf(list.get(position).getLat())  );
			lon.setText( "lon: " + String.valueOf(list.get(position).getLon())  );
			time.setText( timeFormat.format(date)  );
			ldate.setText( dateFormat.format(date));
			
			alt.setText( "alt: " +  String.valueOf(list.get(position).getAltitude()) );
			
			return row;
			
		}
	
	}
	
	
	
	public void onCreate(Bundle savedInstanceState) 
	{
		Log.d(TAG, "On create");
        super.onCreate(savedInstanceState);
        
        positionStorage = ((LostApplication) this.getApplication()).getPositionStorage();
        
        
        setListAdapter(new PosListAdapter(this, R.layout.list_pos, positionStorage.getAll()));
	
        Log.d(TAG, "On create after");
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
