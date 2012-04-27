package com.gradiuss.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartGameActivity extends Activity implements View.OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startgame);
         
        Button bStartGame = (Button) findViewById(R.id.bStartGame);
        Button bHighScore = (Button) findViewById(R.id.bHighScore);
        Button bOptions = (Button) findViewById(R.id.bOptions);
        Button bAbout = (Button) findViewById(R.id.bAbout);
        Button bHelp = (Button) findViewById(R.id.bHelp);

        bStartGame.setOnClickListener(this);
        bHighScore.setOnClickListener(this);
        bOptions.setOnClickListener(this);
        bAbout.setOnClickListener(this);
        bHelp.setOnClickListener(this);

    }

	public void onClick(View v) {
		Toast tmpToast;
		switch (v.getId()) {
		case R.id.bStartGame:
			startActivity(new Intent("android.intent.action.GAMEVIEWACTIVITY"));
			break;
		case R.id.bOptions:
			tmpToast = Toast.makeText(this, "Options Screen! (Not Working yet!)", Toast.LENGTH_SHORT);
			tmpToast.show();
			showOptions(null);
			break;
		case R.id.bHighScore:
			startActivity(new Intent("android.intent.action.SQLVIEWACTIVITY"));
			break;
		case R.id.bAbout:
			showAbout(new Intent("android.intent.action.ABOUTACTIVITY"));
			break;
		case R.id.bHelp:
			showAbout(new Intent("android.intent.action.HELPACTIVITY"));
			break;
			
		}
	}

	private void startGame(Intent startGameIntent) {
		startActivity(startGameIntent);
	}
	
	private void showHighScores(Intent showHighScoresIntent) {
		startActivity(showHighScoresIntent);
	}
	
	private void showOptions(Intent showOptionsIntent) {

	}
	
	private void showAbout(Intent showAboutIntent) {
		startActivity(showAboutIntent);
	}
	
	private void showHelp(Intent showHelpIntent) {
		startActivity(showHelpIntent);

	}
	
}











