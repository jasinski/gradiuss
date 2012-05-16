package com.gradiuss.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gradiuss.game.highscore.HighScores;

public class GameOverActivity extends Activity implements OnClickListener {
	private static final String TAG = GameOverActivity.class.getSimpleName();
	Button bHighScores, bMain, bRestart, bSaveScores;
	EditText etName;
	TextView tvScore;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Buttons and EditTexts connected to the xml
        setContentView(R.layout.gameover);
        etName = (EditText) findViewById(R.id.etPlayer);
        tvScore = (TextView) findViewById(R.id.tvScore);
        bMain = (Button) findViewById(R.id.bMain);
        bRestart = (Button) findViewById(R.id.bRestart);
        bSaveScores = (Button) findViewById(R.id.bSaveScores); 

        Bundle bundleScore = getIntent().getExtras();
		int score = bundleScore.getInt("score");
		
		bSaveScores.setOnClickListener(this);
		bMain.setOnClickListener(this);
        bRestart.setOnClickListener(this);  
        
        Log.d(TAG, "" + score);
        tvScore.setText("" + score);
        
    }


	public void onClick(View v) {
		
		
		switch (v.getId()){
		// Adding the given values from the editText and inserting them to the table
		case R.id.bSaveScores:
			
			String name = etName.getText().toString();
			String score = tvScore.getText().toString();
			
			if (!name.equals("")) {
			
				boolean didItWork = true;
				try{
				
				
				
				HighScores entry = new HighScores(GameOverActivity.this);
				entry.open();
				entry.createEntry(name, score);
	//			Log.d(TAG,entry.getData());
				entry.close();
				
				
				}catch (Exception e){
					didItWork = false;
					String error = e.toString();
					
					
				}finally{
					//Popup messages after inserting a value in the table
					if(didItWork){
						Intent i = new Intent("android.intent.action.HIGHSCORESACTIVITY");
						startActivity(i);
						finish();
					}
				}
				finish();
			} else {
				Toast toastNoName = Toast.makeText(this, "Fill in the field to save your score!", Toast.LENGTH_LONG);
				toastNoName.show();
			}
			
			break;
		case R.id.bMain:
			startActivity(new Intent(this, StartGameActivity.class));
			finish();
			break;
			
		case R.id.bRestart:
			startActivity(new Intent(this, GameViewActivity.class));
			finish();
			break;
		}
		
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
}