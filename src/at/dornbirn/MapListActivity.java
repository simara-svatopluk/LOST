package at.dornbirn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MapListActivity extends Activity{
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.map_options);	
    	
    	Button buttonNew = (Button) findViewById(R.id.button1);
    	buttonNew.setOnClickListener(new View.OnClickListener() {
    		BufferedReader reader = null;
    		private ArrayList<String> countries = new ArrayList<String>(); 
			ArrayList<String> tokens = new ArrayList<String>();
			
			@Override
			public void onClick(View v) {
				try{
					URL url = new URL("http://download.mapsforge.org/maps/europe/");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					
					reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String str = "";
					
					while((str = reader.readLine()) != null){
						if(str.contains(".map\"") == true){
							String tkn = "\"";
							StringTokenizer tokenizer = new StringTokenizer(str, tkn);
							
							while(tokenizer.hasMoreElements()){
								String token = (String) tokenizer.nextElement();
								tokens.add(token);	
							}							
						}
					}
					
					for(String var : tokens){
						if(var.contains(".map") == true && var.contains(">") == false){
							countries.add(var);	
						}
					}
					
					Intent intent = new Intent(getApplicationContext(), DownloadNewMapActivity.class); 
					Bundle bundle = new Bundle();  
					bundle.putStringArrayList("countries", countries);  
					intent.putExtras(bundle);  
					startActivity(intent);  
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				finally{
					if(reader != null){
						try{
							reader.close();
						}catch(IOException e){
							e.printStackTrace();
						}
					}
				}
			}
		});
    	
    	Button buttonCurrent = (Button) findViewById(R.id.button2);
    	buttonCurrent.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), CurrentMapsActivity.class);
				startActivity(intent);
			}
    	});
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
    		case R.id.itemMaps:
    			return true;
    		case R.id.itemStats:
    			startActivity(new Intent(this, StatisticsActivity.class));
    		case R.id.itemNewPlan:	    		
    			startActivity(new Intent(this, PlanActivity.class));
    			return true;
    		case R.id.itemCurrentPlan:
    			startActivity(new Intent(this, LostActivity.class));
    			return true;
    		case R.id.itemSettings:
    			startActivity(new Intent(this, SettingsActivity.class));
    			return true;
    	}
    
    	return super.onOptionsItemSelected(item);
    }
}
