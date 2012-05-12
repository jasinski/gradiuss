package com.gradiuss.game;

import com.gradiuss.game.audio.GameMusic;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

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
					
					// Game time
					gameView.levels.get(gameView.levelPointer).setTotalGameTime(System.nanoTime() - gameView.levels.get(gameView.levelPointer).getStartGameTime());

					
					// TODO - REMOVE: LOGGIN GAME TIME
//					Log.d(TAG, "Total game time:" + gameView.totalGameTime + ", TimeDifference: " + (gameView.totalGameTime - old));
					
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
		// TESTING
		gameView.levels.get(gameView.levelPointer).updateLevel();
		// TESTING
//		gameView.updateState();
	}
	
	/**
	 * Wrapper for the actual rendering method in the game view.
	 */
	public void renderGameState(Canvas canvas) {
		// TESTING
		gameView.levels.get(gameView.levelPointer).renderLevel(canvas);
		// TESTING
//		gameView.renderState(canvas);
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void pauseThread() {
		// TODO: TEMPORARY
		GameMusic.stopMusic();
		
		running = false;
		while (true) {
			try {
				join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
	public void resumeThread() {
		running = true;
	}

}

