package at.dornbirn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MapListActivity extends Activity {
	AlertDialog.Builder alertDialogBuilder;
	AlertDialog alertDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_options);

		String[] Options = { "Download new map", "Current maps", "Reset" };
		ListView list = (ListView) findViewById(R.id.list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.listitem, Options);
		list.setAdapter(adapter);

		list.setClickable(true);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				if (position == 0) {
					BufferedReader reader = null;
					ArrayList<String> countries = new ArrayList<String>();
					ArrayList<String> tokens = new ArrayList<String>();

					try {
						if (isNetworkConnected(MapListActivity.this) == true) {
							URL url = new URL(
									"http://download.mapsforge.org/maps/europe/");
							HttpURLConnection con = (HttpURLConnection) url
									.openConnection();

							reader = new BufferedReader(new InputStreamReader(
									con.getInputStream()));
							String str = "";

							while ((str = reader.readLine()) != null) {
								if (str.contains(".map\"") == true) {
									String tkn = "\"";
									StringTokenizer tokenizer = new StringTokenizer(
											str, tkn);

									while (tokenizer.hasMoreElements()) {
										String token = (String) tokenizer
												.nextElement();
										tokens.add(token);
									}
								}
							}

							for (String var : tokens) {
								if (var.contains(".map") == true
										&& var.contains(">") == false) {
									countries.add(var);
								}
							}

							Intent intent = new Intent(getApplicationContext(),
									DownloadNewMapActivity.class);
							Bundle bundle = new Bundle();
							bundle.putStringArrayList("countries", countries);
							intent.putExtras(bundle);
							startActivity(intent);
						} else {
							alertDialog.show();
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (reader != null) {
							try {
								reader.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				} else if(position == 1) {
					Intent intent = new Intent(getApplicationContext(),
							CurrentMapsActivity.class);
					startActivity(intent);
				}
				else{
					
				}
			}
		});

		alertDialogBuilder = new AlertDialog.Builder(MapListActivity.this);
		alertDialogBuilder.setTitle("Warning!");
		alertDialogBuilder.setMessage("No connection.");
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		alertDialog = alertDialogBuilder.create();
	}

	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isAvailable() && cm
				.getActiveNetworkInfo().isConnected());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(this).inflate(R.menu.menu1, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.itemMaps:
			return true;
		case R.id.itemStats:
			startActivity(new Intent(this, StatisticsActivity.class));
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
