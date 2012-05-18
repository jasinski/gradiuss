package com.gradiuss.game.highscore;



import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.gradiuss.game.R;
import com.gradiuss.game.StartGameActivity;

//Opening the Highscore View and showing the scores
public class HighScoresActivity extends Activity implements OnClickListener {
	private static final String TAG = HighScoresActivity.class.getSimpleName();
	
	TableLayout tlScores;
	Button bMain;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlview);
		HighScores info = new HighScores(this);	
		info.open();
		
		// UI
		tlScores = (TableLayout) findViewById(R.id.tlScores);
		
		// Fetching data from the Highscore object and storing it.
		ArrayList<String[]> highScoreList = info.getData();
		
		for (int i = 0; i < highScoreList.size(); i++) {
			
			// Populating tablelayout
			// Table rows
			TableRow trTemp = new TableRow(this);
			trTemp.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));	
			
			// Column: Name
			TextView tvTemp1 = new TextView(this);
			tvTemp1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0.5f));
			tvTemp1.setGravity(Gravity.CENTER);
			
			// First entry yellow and bold
			if (i == 0) {	
				tvTemp1.setTextColor(Color.YELLOW);
				tvTemp1.setTypeface(null, Typeface.BOLD);
			} else {
				tvTemp1.setTextColor(Color.WHITE);
			}
			tvTemp1.setText(highScoreList.get(i)[0]);
			trTemp.addView(tvTemp1);
			
			// Column: Score
			TextView tvTemp2 = new TextView(this);
			tvTemp2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0.5f));
			tvTemp2.setGravity(Gravity.CENTER);
			
			// First entry yellow and bold
			if (i == 0) {
				tvTemp2.setTextColor(Color.YELLOW);
				tvTemp2.setTypeface(null, Typeface.BOLD);
			} else {
				tvTemp2.setTextColor(Color.WHITE);
			}
			tvTemp2.setText(highScoreList.get(i)[1]);
			trTemp.addView(tvTemp2);
			
			// Adding tablerow to tablelayout
			tlScores.addView(trTemp);
		}
		
		// Closing DB
		info.close();
		
		// Init UI
		bMain = (Button) findViewById(R.id.bMain);
		bMain.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bMain:
			// Main menu
			startActivity(new Intent(this, StartGameActivity.class));
			break;
		}
		finish();
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}

}
