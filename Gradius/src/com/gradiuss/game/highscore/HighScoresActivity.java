package com.gradiuss.game.highscore;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gradiuss.game.R;
import com.gradiuss.game.StartGameActivity;

//Opening the Highscore View and showing the scores
public class HighScoresActivity extends Activity implements OnClickListener {
	private static final String TAG = HighScoresActivity.class.getSimpleName();
	
	Button bMain;
	
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
		
		bMain = (Button) findViewById(R.id.bMain);
		bMain.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bMain:
			startActivity(new Intent(this, StartGameActivity.class));
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
	}

}
