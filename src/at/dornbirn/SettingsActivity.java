package at.dornbirn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SettingsActivity extends Activity 
{

	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        
        // no settings yet.. just start PosView activity..
        startActivity(new Intent(this, PosViewActivity.class));
	
		
	}
}
