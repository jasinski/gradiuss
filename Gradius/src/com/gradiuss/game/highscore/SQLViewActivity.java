package com.gradiuss.game.highscore;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.gradiuss.game.R;
import com.gradiuss.game.models.TypeOneProjectile;

//Opening the Highscore View and showing the scores
public class SQLViewActivity extends Activity{
	private static final String TAG = SQLViewActivity.class.getSimpleName();
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlview);
		TextView tv = (TextView) findViewById(R.id.tvSQLinfo);
		HighScores info = new HighScores(this);	
		info.open();
		//Fetching data from the Highscore object and storing it.
		String data = info.getData();
		info.close();
		//Showing the highscore data in a textview
		tv.setText(data);
		
	}

}
