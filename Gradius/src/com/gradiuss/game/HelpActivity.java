package com.gradiuss.game;

import android.app.Activity;
import android.os.Bundle;

public class HelpActivity extends Activity{
	private static final String TAG = HelpActivity.class.getSimpleName();
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		
	}	
	
	@Override
	public void onBackPressed() {
		finish();
	}
}
