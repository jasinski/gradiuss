package com.gradiuss.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gradiuss.game.levels.Level;
import com.gradiuss.game.levels.LevelOne;
import com.gradiuss.game.models.Enemy;
import com.gradiuss.game.models.Explosion;
import com.gradiuss.game.models.SpaceShip;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// :::::::::::::::::::::::::::::::::::::::::::::: Fields ::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	
	//Sound variables 
	private SoundPool sounds;
	private int sExplosion;
	MediaPlayer explosion;
	
	//Random generator
	Random r;
	
	private static final String TAG = GameView.class.getSimpleName();
	
	// Levels
	public ArrayList<Level> levels;
	public int levelPointer;
	
	// Game Loop
	public GameLoopThread gameLoop;
	
	// GameView
	public static int width;
	public static int height;
	
	// Game time
	long startGameTime;
	long totalGameTime = 0;
	
	// SpaceShip
	public SpaceShip spaceShip;
	
	// Enemies
	public List<Enemy> enemies;
	
	// Explosions
	public List<Explosion> explosions;
	float explosionFrameTime; // Measures how long time an explosion-frame last.
	
	// BITMAPS
	// Background
	Bitmap bmSpaceShip;
	Bitmap bmSpaceShipHit;
	Bitmap bmTypeOneProjectile1;
	Bitmap bmTypeOneProjectile2;
	Bitmap bmAsteroid;
	Bitmap bmAlienShip;
	
	// Array of Explosion Frames
	List<Bitmap> bmExplosionFrames = new ArrayList<Bitmap>(9);
		
	public GameView(Context context, AttributeSet attributes) {
		super(context, attributes);
		initGameView();
	}
	
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// :::::::::::::::::::::::::::::::::::::::::::::: Initializing ::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	// Loading resources like images, music etc... and starting the game loop!
	public void surfaceCreated(SurfaceHolder holder) {
		
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

	

	public void initLevels() {
		levels = new ArrayList<Level>();
		LevelOne levelOne = new LevelOne(this.getContext(), width, height);
		levelOne.initializeLevel();
		levels.add(levelOne);
		levelPointer = 0;
	}
	

	public void updateLevel() {
		levels.get(levelPointer).initializeLevel();
	}
	
	public void renderLevel(Canvas canvas) {
		levels.get(levelPointer).renderLevel(canvas);
	}
	
	private void initGameView() {
		getHolder().addCallback(this);
		gameLoop = new GameLoopThread(getHolder(), this);
		setFocusable(true);
	}
        
}