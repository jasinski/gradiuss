package com.gradiuss.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gradiuss.game.models.SpaceShip;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = GameView.class.getSimpleName();
	private GameLoopThread gameLoop;
	
	public SpaceShip spaceShip;
	
	// This might be necessary later, so will not remove yet
//	public GameView(Context context) {
//		super(context);
//		initGameView();
//	}
	
	public GameView(Context context, AttributeSet attributes) {
		super(context, attributes);
		initGameView();
	}
	
	private void initGameView() {
		getHolder().addCallback(this);
		gameLoop = new GameLoopThread(getHolder(), this);
		setFocusable(true);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO: Fixa till senare!
	}
	
	public void initLevel() {
		initSpaceShip();
		initEnemies();
	}

	private void initSpaceShip() {
		// SpaceShip
		Bitmap spaceShipBitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.spaceship);
		spaceShip = new SpaceShip(spaceShipBitmap, getWidth()/2, getHeight()-spaceShipBitmap.getHeight(), 5, 5);
		spaceShip.setVx(10);
		spaceShip.setVisible(true);
	}
	
	private void initEnemies() {
		// TODO: Enemies
	}
	
	// Loading resources like images, music etc... and starting the game loop!
	public void surfaceCreated(SurfaceHolder holder) {
		
		// Loading level (Resources)
		initLevel();
		
		// Starting game loop
		gameLoop.setRunning(true);
		gameLoop.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				gameLoop.join();
				retry = false;
			} catch (InterruptedException e) {
				// Try again, shutting down the thread(game loop)
			}
		}
		
	}
	
	// Updating the states for all the game objects
	public void updateState() {
		// TODO: GÖR EGNA METODER FÖR VARJE OBJECT SEN!
		// Collisions with the Edges
		if (spaceShip.getX() + spaceShip.getBitmap().getWidth()/2 >= getWidth()) {
			spaceShip.setMoveRight(false);
		}
		if ((spaceShip.getX() - spaceShip.getBitmap().getWidth()/2 <= 0)) {
			spaceShip.setMoveLeft(false);
		}
		spaceShip.updateState();	
	}
	
	// This might be necessary later, so will not remove yet
//	@Override
//	protected void onDraw(Canvas canvas) {
//		canvas.drawColor(Color.BLACK);
//		spaceShip.draw(canvas);
//	}
	
	// Rendering the game state
	public void renderState(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		spaceShip.draw(canvas);
		//onDraw(canvas);
	}

	public class GameLoopThread extends Thread {
		private final String TAG = GameLoopThread.class.getSimpleName();
		private boolean running;
		private SurfaceHolder surfaceHolder;
		private GameView gameView;
		
		public GameLoopThread(SurfaceHolder surfaceHolder, GameView gameView) {
			this.surfaceHolder = surfaceHolder;
			this.gameView = gameView;
		}
		
		@Override
		public void run() {
			
			Canvas canvas;
			
			Log.d(TAG, "Starting game loop");
			while (running) {
				canvas = null;
				try {
					
					// Locking canvas so that objects can draw themselves on it
					canvas = this.surfaceHolder.lockCanvas();
					
					synchronized (surfaceHolder) {

						// Update the game
						updateGameState();
						
						// Render the game
						renderGameState(canvas);
					}
				} finally {
					if (canvas != null) {
						
						// Unlocking canvas and displaying it
						surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
				
			}
		}
		
		/**
		 * Wrapper for the actual update method in the game view.
		 */
		public void updateGameState() {
			gameView.updateState();
		}
		
		/**
		 * Wrapper for the actual rendering method in the game view.
		 */
		public void renderGameState(Canvas canvas) {
			gameView.renderState(canvas);
		}
		
		public void setRunning(boolean running) {
			this.running = running;
		}

	}
	
}