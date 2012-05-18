package com.gradiuss.game;

import android.app.Activity;
import android.os.Bundle;


public class AboutActivity extends Activity {
	private static final String TAG = AboutActivity.class.getSimpleName();
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);	
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
}