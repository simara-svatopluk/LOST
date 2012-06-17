package at.dornbirn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DownloadNewMapActivity extends Activity{
	private ListView mapNames;  
	private ArrayList<String> incomingDataList; 
	BufferedReader reader = null;

	@Override 
	 protected void onCreate(Bundle savedInstanceState) {  
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.maps_list);
		 
		 mapNames = (ListView) findViewById(R.id.listViewMaps);
		 mapNames.setClickable(true);
		 
		 mapNames.setOnItemClickListener(new OnItemClickListener() { 			 
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					
					Object o = mapNames.getItemAtPosition(position);  
					File externalStorage = Environment.getExternalStorageDirectory(); 
					String path = externalStorage.getAbsolutePath();
					
					URL url;
					try {				
						url = new URL("http://download.mapsforge.org/maps/europe/" + o.toString());
						
						HttpURLConnection con = (HttpURLConnection) url.openConnection();
						
						reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
						File file = new File(path + File.separator + "maps" + File.separator + o.toString() + File.separator);
						
						FileOutputStream fos;
						fos = new FileOutputStream(file);
						
						InputStream inputStream = con.getInputStream();
						byte[] buffer = new byte[1024];
						int bufferLength = 0;
						
						while((bufferLength = inputStream.read(buffer)) > 0){
							fos.write(buffer, 0, bufferLength);
						}
						fos.close();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			} 
		});
		 	 
		 Bundle extra = getIntent().getExtras();
		 incomingDataList = extra.getStringArrayList("countries");  
		 mapNames.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, incomingDataList));	 
	 }
}
