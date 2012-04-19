package com.gradiuss.game;

import com.gradiuss.game.models.SpaceShip;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class GameViewActivity extends Activity {
	
	private static final String TAG = GameViewActivity.class.getSimpleName();
	GameView gameView;
	Button bLeftPad; // Move spaceship left
	Button bRightPad; // Move spaceship right
	Button bFire; // Fire projectiles
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        // Window settings
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // Inflating layout
        setContentView(R.layout.gamelayout);
       
        // Initializing view
        gameView = (GameView) findViewById(R.id.gameView);
        bLeftPad = (Button) findViewById(R.id.bLeftPad);
        bRightPad = (Button) findViewById(R.id.bRightPad);
        bFire = (Button) findViewById(R.id.bFire);
        
        // Setting onTouch listeners
        bLeftPad.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				// Move spaceship left
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					bLeftPad.setPressed(true);
					gameView.spaceShip.setMoveLeft(true);
				}
				
				// Stop moving spaceship left
				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.spaceShip.setMoveLeft(false);
					bLeftPad.setPressed(false);
				}
				return true;
			}
		});
        
        bRightPad.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				// Move spaceship right
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					bRightPad.setPressed(true);
					gameView.spaceShip.setMoveRight(true);
				}
				
				// Stop moving spaceship right
				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.spaceShip.setMoveRight(false);
					bRightPad.setPressed(false);
				}
				return true;
			}
		});
        
        bFire.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				// Start shooting
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					gameView.spaceShip.setShooting(true);
					Log.d(TAG, "shooting...");
				}
				
				// Stop shooting
				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.spaceShip.setShooting(false);
					Log.d(TAG, "...stopped shooting");
				}
				return true;
			}
		});
        
    }

	@Override
	protected void onDestroy() {
		Log.d(TAG, "Destroying...");
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "Stopping...");
		super.onStop();
	}
	
}