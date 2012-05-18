package com.gradiuss.game.highscore;



import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
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
		//Fetching data from the Highscore object and storing it.
		
		
//		String data = info.getData();
		
		// TESTING
		tlScores = (TableLayout) findViewById(R.id.tlScores);
		ArrayList<String[]> highScoreList = info.getData();
		
		for (int i = 0; i < highScoreList.size(); i++) {
			
//			Log.d(TAG, i + ": " + highScoreList.get(i)[0] + ", " +highScoreList.get(i)[1]);
			TableRow trTemp = new TableRow(this);
			trTemp.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));	
			
			TextView tvTemp1 = new TextView(this);
			tvTemp1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0.5f));
			tvTemp1.setGravity(Gravity.CENTER);
			if (i == 0) {
				tvTemp1.setTextColor(Color.YELLOW);
				tvTemp1.setTypeface(null, Typeface.BOLD);
			} else {
				tvTemp1.setTextColor(Color.WHITE); // #705090
			}
			tvTemp1.setText(highScoreList.get(i)[0]);
			trTemp.addView(tvTemp1);
			
			TextView tvTemp2 = new TextView(this);
			tvTemp2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0.5f));
			tvTemp2.setGravity(Gravity.CENTER);
			if (i == 0) {
				tvTemp2.setTextColor(Color.YELLOW);
				tvTemp2.setTypeface(null, Typeface.BOLD);
			} else {
				tvTemp2.setTextColor(Color.WHITE);
			}
			tvTemp2.setText(highScoreList.get(i)[1]);
			trTemp.addView(tvTemp2);
			
			tlScores.addView(trTemp);
		}
		
		info.close();
		//Showing the highscore data in a textview
//		tv.setText(data);
		
		bMain = (Button) findViewById(R.id.bMain);
		bMain.setOnClickListener(this);
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bMain:
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
