package com.gradiuss.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gradiuss.game.highscore.HighScores;

public class GameOverActivity extends Activity implements OnClickListener {
	private static final String TAG = GameOverActivity.class.getSimpleName();
	
	// UI components
	Button bHighScores, bMain, bRestart, bSaveScores;
	EditText etName;
	TextView tvScore;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);
       
        // Initializing UI
        etName = (EditText) findViewById(R.id.etPlayer);
        tvScore = (TextView) findViewById(R.id.tvScore);
        bMain = (Button) findViewById(R.id.bMain);
        bRestart = (Button) findViewById(R.id.bRestart);
        bSaveScores = (Button) findViewById(R.id.bSaveScores); 

        // Getting the score
        Bundle bundleScore = getIntent().getExtras();
		int score = bundleScore.getInt("score");
		
		// Setting listeners
		bSaveScores.setOnClickListener(this);
		bMain.setOnClickListener(this);
        bRestart.setOnClickListener(this);  
        
        // Displaying score
        tvScore.setText("" + score);
        
    }


	public void onClick(View v) {
		
		switch (v.getId()){
		// Highscore screen
		case R.id.bSaveScores:
			
			String name = etName.getText().toString();
			String score = tvScore.getText().toString();
			
			if (!name.equals("")) {
			
				boolean didItWork = true;
				try{
				
				HighScores entry = new HighScores(GameOverActivity.this);
				entry.open();
				entry.createEntry(name, score);
				entry.close();
				
				}catch (Exception e){
					didItWork = false;
					
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
			// Main menu
			startActivity(new Intent(this, StartGameActivity.class));
			finish();
			break;
			
		case R.id.bRestart:
			// Play again
			startActivity(new Intent(this, GameViewActivity.class));
			finish();
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
	}
}