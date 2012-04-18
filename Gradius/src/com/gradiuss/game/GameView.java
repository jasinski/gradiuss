package com.gradiuss.game;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gradiuss.game.models.Projectile;
import com.gradiuss.game.models.SpaceShip;
import com.gradiuss.game.models.TypeOneProjectile;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	
	private static final String TAG = GameView.class.getSimpleName();
	private GameLoopThread gameLoop;
	
	// GameObjects
	public SpaceShip spaceShip;
	public List<Projectile> projectiles;
	int projectileType = 0;
	
	public GameView(Context context, AttributeSet attributes) {
		super(context, attributes);
		initGameView();
	}
	
	// :::::::::::::::::::::::::::::::::::::::::::::: Initializing ::::::::::::::::::::::::::::::::::::::::::::::
	
	// Loading resources like images, music etc... and starting the game loop!
	public void surfaceCreated(SurfaceHolder holder) {
		
		// Loading level (Resources)
		initGameObjects();
		
		// Starting game loop
		gameLoop.setRunning(true);
		gameLoop.start();
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO: Fixa till senare!
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
	
	private void initGameView() {
		getHolder().addCallback(this);
		gameLoop = new GameLoopThread(getHolder(), this);
		setFocusable(true);
	}

	
	public void initGameObjects() {
		initSpaceShip();
		initProjectiles();
		initEnemies();
	}

	private void initSpaceShip() {
		// SpaceShip
		Bitmap spaceShipBitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.spaceship);
		spaceShip = new SpaceShip(spaceShipBitmap, getWidth()/2, getHeight()-spaceShipBitmap.getHeight(), 5, 5);
		spaceShip.setVx(10);
		spaceShip.setVisible(true);
	}
	
	private void initProjectiles() {
		// Projectiles
		projectiles = new ArrayList<Projectile>();
	}
	
	private void initEnemies() {
		// TODO: Enemies
	}
	
	// :::::::::::::::::::::::::::::::::::::::::::::: Updating ::::::::::::::::::::::::::::::::::::::::::::::
	
	// Updating the states for all the game objects
	public void updateState() {
		// Update SpaceShip
		updateSpaceShip();
		updateProjectiles();
	}
	
	public void updateProjectiles() {
		// Skjuter projektiler
		if (spaceShip.isShooting()) {
			addProjectile(spaceShip.getX(), spaceShip.getY());
		}
		
		for (int i = projectiles.size() - 1; i >= 0; i--) {
			projectiles.get(i).updateState();
			if (projectiles.get(i).isVisible() == false) {
				projectiles.remove(i);
			}
		}
		// Skjuter projektiler
	}
	
	private void updateSpaceShip() {
		// Collisions with the Edges
		if (spaceShip.getX() + spaceShip.getBitmap().getWidth()/2 >= getWidth()) {
			spaceShip.setMoveRight(false);
		}
		if ((spaceShip.getX() - spaceShip.getBitmap().getWidth()/2 <= 0)) {
			spaceShip.setMoveLeft(false);
		}
		spaceShip.updateState();
	}
	
	public void addProjectile(float x, float y) {
		// Adding projectiles
		Projectile projectile;
		switch (projectileType) {
		case 0:
			projectile = new TypeOneProjectile(BitmapFactory.decodeResource(getResources(), R.drawable.projectile1), x, y - spaceShip.getBitmap().getHeight()/2);
			projectile.setVisible(true);
			projectile.setMoveUp(true);
			projectile.setVy(50);
			projectiles.add(projectile);
			break;
		case 1:
			projectile = new TypeOneProjectile(BitmapFactory.decodeResource(getResources(), R.drawable.projectile2), x, y - spaceShip.getBitmap().getHeight()/2);
			projectile.setVisible(true);
			projectile.setMoveUp(true);
			projectile.setVy(50);
			projectiles.add(projectile);
			break;
		}
		
	}
	
	// :::::::::::::::::::::::::::::::::::::::::::::: Rendering ::::::::::::::::::::::::::::::::::::::::::::::
	
	// Rendering the game state
	public void renderState(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		renderSpaceShip(canvas);
		renderProjectiles(canvas);
	}
	
	public void renderSpaceShip(Canvas canvas) {
		spaceShip.draw(canvas);
	}
	
	public void renderProjectiles(Canvas canvas) {
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).draw(canvas);
		}
	}
	
	// :::::::::::::::::::::::::::::::::::::::::::::: GameLoopThread ::::::::::::::::::::::::::::::::::::::::::::::

	public class GameLoopThread extends Thread {
		private final String TAG = GameLoopThread.class.getSimpleName();
		private boolean running;
		private SurfaceHolder surfaceHolder;
		private GameView gameView;
		
		// Frames per second
		private final static int MAX_FPS = 50;
		// Max amount of frames skipped
		private final static int MAX_FRAMES_SKIPPED = 5;
		// Length of the period
		private final static int UPDATE_RENDER_PERIOD = 1000 / MAX_FPS;
		
		public GameLoopThread(SurfaceHolder surfaceHolder, GameView gameView) {
			this.surfaceHolder = surfaceHolder;
			this.gameView = gameView;
		}
		
		@Override
		public void run() {
			
			Canvas canvas;
			
			// FPS
			long startTime;
			long endTime;
			long timeDifference;
			int sleepTime;
			int skippedFrames;
			
			Log.d(TAG, "Starting game loop");
			while (running) {
				canvas = null;
				try {
					
					// Locking canvas so that objects can draw themselves on it
					canvas = this.surfaceHolder.lockCanvas();
					
					synchronized (surfaceHolder) {
						
						// Start timer
						startTime = System.currentTimeMillis();
						
						// Update the game
						updateGameState();
						
						// Render the game
						renderGameState(canvas);
						
						// Stop timer and measure the timedifference as well as how long we shall sleep
						endTime = System.currentTimeMillis();
						timeDifference = startTime - endTime;
						sleepTime = (int) (UPDATE_RENDER_PERIOD - timeDifference);
						
						// If sleepTime is larger than 0 then sleep
						if (sleepTime > 0) {
							try {
								sleep(sleepTime);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						
						// If sleepTime is smaller than 0 we need to catch up
						skippedFrames = 0;
						while (sleepTime < 0 && skippedFrames < MAX_FRAMES_SKIPPED) {
							updateGameState();
							sleepTime = sleepTime + UPDATE_RENDER_PERIOD;
							skippedFrames++;
						}
						
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