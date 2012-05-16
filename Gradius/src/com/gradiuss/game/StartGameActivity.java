package com.gradiuss.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartGameActivity extends Activity implements View.OnClickListener {
	private static final String TAG = StartGameActivity.class.getSimpleName();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startgame);
        
        int test = 2345;
        int[] array = new int[4];
        for (int i = array.length-1; i >= 0; i--) {
        	array[i] = test % 10;
        	test = test / 10;
        }
        
        for (int j = 0; j < array.length; j++) {
        	Log.d(TAG, "array=" + array[j]);
        }
        
         
        Button bStartGame = (Button) findViewById(R.id.bStartGame);
        Button bHighScore = (Button) findViewById(R.id.bHighScore);
        Button bOptions = (Button) findViewById(R.id.bOptions);
        Button bAbout = (Button) findViewById(R.id.bAbout);
        Button bHelp = (Button) findViewById(R.id.bHelp);
        Button bQuitGame = (Button) findViewById(R.id.bQuitGame);

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
			startActivity(new Intent("android.intent.action.GAMEVIEWACTIVITY"));
			break;
		case R.id.bOptions:
			startActivity(new Intent("android.intent.action.OPTIONSACTIVITY"));
			break;
		case R.id.bHighScore:
			startActivity(new Intent("android.intent.action.HIGHSCORESACTIVITY"));
			break;
		case R.id.bAbout:
			showAbout(new Intent("android.intent.action.ABOUTACTIVITY"));
			break;
		case R.id.bHelp:
			showAbout(new Intent("android.intent.action.HELPACTIVITY"));
			break;
		case R.id.bQuitGame:
			// TODO: EXIT GAME !!!
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











