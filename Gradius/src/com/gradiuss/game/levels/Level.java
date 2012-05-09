package com.gradiuss.game.levels;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;

import com.gradiuss.game.models.SpaceShip;

/**
 * Level
 * 
 * @author mike
 * 
 */
public abstract class Level {
	
	// Tag for logging
	private static final String TAG = Level.class.getSimpleName();
	
	// Game time
	private long startGameTime;
	private long totalGameTime = 0;
	
	// Context and resources
	private Context context;
	private Resources resources;
	
	// GameView variables
	private int screenHeight;
	private int screenWidth;
	
	// SpaceShip
	private SpaceShip spaceShip;
	
	// TODO: Level states
	private List<LevelState> states;
	private int statePointer;

	public Level(Context context, int screenWidth, int screenHeight) {
		this.states = new ArrayList<LevelState>();
		this.context = context;
		this.resources = this.context.getResources();
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	
	public Context getContext() {
		return context;
	}

	public Resources getResources() {
		return resources;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public int getScreenWidth() {
		return screenWidth;
	}
	
	public long getStartGameTime() {
		return startGameTime;
	}
	
	public void setTotalGameTime(long totalGameTime) {
		this.totalGameTime = totalGameTime;
	}
	
	public long getTotalGameTime() {
		return totalGameTime;
	}
	
	public void setSpaceShip(SpaceShip spaceShip) {
		this.spaceShip = spaceShip;
	}
	
	public SpaceShip getSpaceShip() {
		return spaceShip;
	}

	/**
	 * Initializing level
	 */
	public abstract void initializeLevel();

	/**
	 * Updating level
	 */
	public abstract void updateLevel();

	/**
	 * Rendering level
	 * @param canvas
	 */
	public abstract void renderLevel(Canvas canvas);
	
	/**
	 * Change weapon
	 */
	public abstract void changeWeapon();

	/**
	 * TODO: NOT USED YET! IF THE LEVELS GET LARGER, THIS CLASS WILL HANDLE DIFFERENT STATES.
	 * FOR EXAMPLE A "BOSS BATTLE" AS THE LAST STATE.
	 * LevelState
	 * 
	 * @author mike
	 * 
	 */
	public abstract class LevelState {

		public abstract void initializeState();

		public abstract void updateState();

		public abstract void drawState(Canvas canvas);

	}

}
