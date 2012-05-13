package at.dornbirn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
		
		Button toMap = (Button) this.findViewById(R.id.statistics_to_map);
		
		toMap.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				StatisticsActivity.this.startActivity(new Intent(StatisticsActivity.this, LostActivity.class));
			}
		});
		
		TextView totalDistance = (TextView) this.findViewById(R.id.statistics_total_distance);
		totalDistance.setText("10");
		
		TextView totalTime = (TextView) this.findViewById(R.id.statistics_total_time);
		totalTime.setText("1.2");
		
		TextView totalSpeed = (TextView) this.findViewById(R.id.statistics_total_speed);
		totalSpeed.setText("4.8");
		
		TextView actualSpeed = (TextView) this.findViewById(R.id.statistics_actual_apeed);
		actualSpeed.setText("62.3");
		
		
		
	}
	
}
