package com.gradiuss.game;

import android.app.Activity;
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
					gameView.levels.get(gameView.levelPointer).getSpaceShip().setMoveLeft(true);
				}
				
				// Stop moving spaceship left
				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.levels.get(gameView.levelPointer).getSpaceShip().setMoveLeft(false);
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
					gameView.levels.get(gameView.levelPointer).getSpaceShip().setMoveRight(true);
				}
				
				// Stop moving spaceship right
				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.levels.get(gameView.levelPointer).getSpaceShip().setMoveRight(false);
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
					gameView.levels.get(gameView.levelPointer).getSpaceShip().setMoveUp(true);
				}
				
				// Stop moving spaceship up
				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.levels.get(gameView.levelPointer).getSpaceShip().setMoveUp(false);
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
					gameView.levels.get(gameView.levelPointer).getSpaceShip().setMoveDown(true);
				}
				
				// Stop moving spaceship down
				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.levels.get(gameView.levelPointer).getSpaceShip().setMoveDown(false);
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
					gameView.levels.get(gameView.levelPointer).changeWeapon();
				}
				return true;
			}
		});
      
        bFire.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				// Start shooting
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					bFire.setPressed(true);
					gameView.levels.get(gameView.levelPointer).getSpaceShip().setShooting(true);
				}
				
				// Stop shooting
				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.levels.get(gameView.levelPointer).getSpaceShip().setShooting(false);
					bFire.setPressed(false);
				}
				return true;
			}
		});
        
        
        
    }
	
	@Override
	protected void onPause() {
//		Log.d(TAG, "Pausing...");
		super.onPause();
		gameView.gameLoop.pauseThread();
//		gameSong.release();
	}
	
	@Override
	protected void onResume() {
//		Log.d(TAG, "Resuming...");
		super.onResume();
		if (gameView.gameLoop == null) {
			gameView.initGameView();
		}
		gameView.gameLoop.resumeThread();
	}
	
	@Override
	protected void onDestroy() {
//		Log.d(TAG, "Destroying...");
		super.onDestroy();
	}

	@Override
	protected void onStop() {
//		Log.d(TAG, "Stopping...");
		super.onStop();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
//		Log.d(TAG, "Saving state...");
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
//		Log.d(TAG, "Restoring state...");
		super.onRestoreInstanceState(savedInstanceState);
	}


	
}