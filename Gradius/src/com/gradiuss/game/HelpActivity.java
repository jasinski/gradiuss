package com.gradiuss.game;

import com.gradiuss.game.highscore.HighScoresActivity;

import android.app.Activity;
import android.os.Bundle;

public class HelpActivity extends Activity{
	private static final String TAG = HelpActivity.class.getSimpleName();
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		
	}	
}
