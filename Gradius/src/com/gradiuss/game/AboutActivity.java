package com.gradiuss.game;

import android.os.Bundle;
import android.app.Activity;
import com.gradiuss.game.R;
import com.gradiuss.game.highscore.SQLViewActivity;


public class AboutActivity extends Activity {
	private static final String TAG = AboutActivity.class.getSimpleName();
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
	}
}