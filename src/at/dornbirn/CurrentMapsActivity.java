package at.dornbirn;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CurrentMapsActivity extends Activity{
	private ArrayList<String> currentMapsList;
	private ListView mapNames;
	TextView t;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps_list);
		currentMapsList = new ArrayList<String>();	
			
		mapNames = (ListView) findViewById(R.id.listViewMaps);
		mapNames.setClickable(true);
		mapNames.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, currentMapsList));
		
		File externalStorage = Environment.getExternalStorageDirectory(); 
		String path = externalStorage.getAbsolutePath();
		
		File mapsDirectory = new File(path + File.separator + "maps");
		
		for (File f : mapsDirectory.listFiles()) { 
		    if (f.isFile())
		    {
		        String name = f.getName(); 
		        currentMapsList.add(name);
		    }	         
		}	
		
		mapNames.setClickable(true);
		
		mapNames.setOnItemClickListener(new OnItemClickListener() { 			 
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					Object o = mapNames.getItemAtPosition(position);
					Intent intent = new Intent(getApplicationContext(), DownloadNewMapActivity.class); 
					Bundle bundle = new Bundle();  
					bundle.putStringArrayList("currentMaps", currentMapsList);  
					intent.putExtras(bundle);  
					startActivity(intent);					
			} 
		});
	}
}
