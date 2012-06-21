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
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DownloadNewMapActivity extends Activity{
	private ListView mapNames;  
	private ArrayList<String> incomingDataList; 
	BufferedReader reader = null;
	ProgressDialog mProgressDialog;
	
	protected Handler handler = new Handler();

	@Override 
	 protected void onCreate(Bundle savedInstanceState) {  
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.maps_list);

		 mProgressDialog = new ProgressDialog(DownloadNewMapActivity.this); 
		 mProgressDialog.setIndeterminate(false); 
		 mProgressDialog.setMax(100); 
		 mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); 
			
		 mapNames = (ListView) findViewById(R.id.listViewMaps);
		 mapNames.setClickable(true);
	
		 mapNames.setOnItemClickListener(new OnItemClickListener() { 			 
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					Object o = mapNames.getItemAtPosition(position);

					String url = "http://download.mapsforge.org/maps/europe/" + o.toString();
					
					DownloadFile downloadFile = new DownloadFile(); 
					downloadFile.execute(url);
			} 
		});
		 
		 Bundle extra = getIntent().getExtras();
		 incomingDataList = extra.getStringArrayList("countries");  
		 mapNames.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, incomingDataList));
		 }
	
	
	 
	private class DownloadFile extends AsyncTask<String, Integer, String>{
		
		private String fileName;

		@Override
		protected String doInBackground(String... sUrl) {
			File externalStorage = Environment.getExternalStorageDirectory(); 
			String path = externalStorage.getAbsolutePath();
			
			//sURL = http://download.mapsforge.org/maps/europe/blabla.map
			String str = sUrl[0];
			String tkn = "/";
			StringTokenizer tokenizer = new StringTokenizer(str, tkn);
			while(tokenizer.hasMoreElements()){
				String token = (String) tokenizer.nextElement();
				fileName = token;
			}							
			
			File file = new File(path + File.separator + "maps" + File.separator + fileName + File.separator);
			
			try {
				URL url = new URL(sUrl[0]);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				
				reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				FileOutputStream fos;
				fos = new FileOutputStream(file);
				
				InputStream inputStream = con.getInputStream();
				byte[] data = new byte[1024];
				long total = 0;
				int count;
				int fileLength = con.getContentLength();
				
				while((count = inputStream.read(data)) > 0){
					total += count;
					publishProgress((int) (total * 100 / fileLength));
					fos.write(data, 0, count);
				}
				fos.flush();
				fos.close();
				inputStream.close();
			
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				mProgressDialog.dismiss();
				e.printStackTrace();
			}
			return null;
		}
		
		 @Override 
		 protected void onPreExecute() { 
			 super.onPreExecute(); 
			 mProgressDialog.setMessage("Downloading...");
		     mProgressDialog.setCancelable(true); 
		     mProgressDialog.show(); 
		 } 

		 @Override 
		 protected void onProgressUpdate(Integer... progress) { 
			 super.onProgressUpdate(progress); 
		     mProgressDialog.setProgress(progress[0]); 
		 } 
		 
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (mProgressDialog.isShowing()) { 
				mProgressDialog.dismiss(); 
	        } 
		}
	}
}

