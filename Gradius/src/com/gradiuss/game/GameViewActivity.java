package com.gradiuss.game;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class GameViewActivity extends Activity {
	
	private static final String TAG = GameViewActivity.class.getSimpleName();
	private GameView gameView;
	private Button bLeftPad; // Move spaceship left
	private Button bRightPad; // Move spaceship right
	private Button bUpPad; // Move spaceship up
	private Button bDownPad; // Move spaceship down
	public static ImageButton bChangeWeapon;
	private Button bFire; // Fire projectiles
	MediaPlayer gameSong;
	MediaPlayer shootsound;

	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Window settings
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // Inflating layout
        setContentView(R.layout.gamelayout);
        
        //Starting song
       gameSong = MediaPlayer.create(GameViewActivity.this, R.raw.gamesong);
       gameSong.start();
        // Initializing view
        gameView = (GameView) findViewById(R.id.gameView);
        bLeftPad = (Button) findViewById(R.id.bLeftPad);
        bRightPad = (Button) findViewById(R.id.bRightPad);
        bUpPad = (Button) findViewById(R.id.bUpPad);
        bDownPad = (Button) findViewById(R.id.bDownPad);
        bChangeWeapon = (ImageButton) findViewById(R.id.bChangeWeapon);
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
				
				Log.d(TAG, event.getX() + "");
				
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
        
        bUpPad.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				// Move spaceship up
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					bUpPad.setPressed(true);
					gameView.spaceShip.setMoveUp(true);
				}
				
				// Stop moving spaceship up
				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.spaceShip.setMoveUp(false);
					bUpPad.setPressed(false);
				}
				return true;
			}
		});
        
        bDownPad.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				// Move spaceship down
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					bDownPad.setPressed(true);
					gameView.spaceShip.setMoveDown(true);
				}
				
				// Stop moving spaceship down
				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.spaceShip.setMoveDown(false);
					bDownPad.setPressed(false);
				}
				return true;
			}
		});
        
        bChangeWeapon.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					
				}
				
				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.changeWeapon();
					Log.d(TAG, "...stopped shooting");
				}
				return true;
			}
		});
	     shootsound= MediaPlayer.create(GameViewActivity.this, R.raw.shootsound);
      
        bFire.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				// Start shooting
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					gameView.spaceShip.setShooting(true);
					Log.d(TAG, "shooting...");
//				     shootsound= MediaPlayer.create(GameViewActivity.this, R.raw.shootsound);
				     shootsound.start();
					
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
	protected void onPause() {
		Log.d(TAG, "Pausing...");
		super.onPause();
		gameView.gameLoop.pauseThread();
		gameSong.release();
	}
	
	@Override
	protected void onResume() {
		Log.d(TAG, "Resuming...");
		super.onResume();
		gameView.gameLoop.resumeThread();
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