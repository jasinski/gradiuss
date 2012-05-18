package com.gradiuss.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartGameActivity extends Activity implements View.OnClickListener {
	private static final String TAG = StartGameActivity.class.getSimpleName();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startgame);
        
        // Initialize UI
        Button bStartGame = (Button) findViewById(R.id.bStartGame);
        Button bHighScore = (Button) findViewById(R.id.bHighScore);
        Button bOptions = (Button) findViewById(R.id.bOptions);
        Button bAbout = (Button) findViewById(R.id.bAbout);
        Button bHelp = (Button) findViewById(R.id.bHelp);
        Button bQuitGame = (Button) findViewById(R.id.bQuitGame);

        // Setting listeners
        bStartGame.setOnClickListener(this);
        bHighScore.setOnClickListener(this);
        bOptions.setOnClickListener(this);
        bAbout.setOnClickListener(this);
        bHelp.setOnClickListener(this);
        bQuitGame.setOnClickListener(this);
    }

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bStartGame:
			// Start Game
			startActivity(new Intent("android.intent.action.GAMEVIEWACTIVITY"));
			break;
		case R.id.bOptions:
			// Options 
			startActivity(new Intent("android.intent.action.OPTIONSACTIVITY"));
			break;
		case R.id.bHighScore:
			// Highscores
			startActivity(new Intent("android.intent.action.HIGHSCORESACTIVITY"));
			break;
		case R.id.bAbout:
			// About us
			showAbout(new Intent("android.intent.action.ABOUTACTIVITY"));
			break;
		case R.id.bHelp:
			// Help
			showAbout(new Intent("android.intent.action.HELPACTIVITY"));
			break;
		case R.id.bQuitGame:
			// Quit game
			finish();
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











