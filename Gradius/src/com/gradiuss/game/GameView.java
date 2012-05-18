package com.gradiuss.game;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gradiuss.game.levels.Level;
import com.gradiuss.game.levels.LevelOne;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = GameView.class.getSimpleName();
	
	// Levels
	public ArrayList<Level> levels;
	public int levelPointer;
	
	// Game Loop
	public static GameLoopThread gameLoop;
	
	// GameView
	public static int width;
	public static int height;
	
	// Game time
	long startGameTime;
	long totalGameTime = 0;
	
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// :::::::::::::::::::::::::::::::::::::::::::::: Initializing ::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	public GameView(Context context, AttributeSet attributes) {
		super(context, attributes);
//		initGameView();
	}
	
	public void initGameView() {
		getHolder().addCallback(this);
		gameLoop = new GameLoopThread(getHolder(), this);
		setFocusable(true);
	}
	
	// Loading resources like images, music etc... and starting the game loop!
	public void surfaceCreated(SurfaceHolder holder) {
		
		initGameView();
		
		// GameView
		width = getWidth();
		height = getHeight();		
		
		// Levels
		initLevels();	
		
		// Starting game loop
		gameLoop.setRunning(true);
		gameLoop.start();

		// Starting time measurement
		startGameTime = System.nanoTime();
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// Should not be called, since the GameView is locked to portrait mode!
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {
		stopGameLoop();
	}
	
	public void stopGameLoop() {
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

	public void initLevels() {
		
		// Initializing level(s)
		levels = new ArrayList<Level>();
		LevelOne levelOne = new LevelOne(this.getContext(), gameLoop, width, height);
		levelOne.initializeLevel();
		levels.add(levelOne);
		
		// Current level = 0
		levelPointer = 0;
	}
	
	public void updateLevel() {
		levels.get(levelPointer).initializeLevel();
	}
	
	public void renderLevel(Canvas canvas) {
		levels.get(levelPointer).renderLevel(canvas);
	}
	
	
        
}