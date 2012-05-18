package com.gradiuss.game.models;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Asteroid extends Enemy {
	private static final String TAG = Asteroid.class.getSimpleName();
	private static final int INITIAL_LIFE = 100;
	private static final int INITIAL_DAMAGE = 80;
	
	private float previousLife;

	public Asteroid(Bitmap bitmap, float x, float y) {
		super(bitmap, x, y);
		setLife(INITIAL_LIFE);
		setDamage(INITIAL_DAMAGE);
		initialize();
	}

	public Asteroid() {
		// Only for testing, should not be used otherwise.
	}
	
	@Override
	public void initialize() {
		SCORE = 100;
	}
	
	public Asteroid(Bitmap bitmap, float x, float y, Rect rectangle) {
		super(bitmap, x, y, rectangle);
		setLife(INITIAL_LIFE);
		setDamage(INITIAL_DAMAGE);
	}

	@Override
	public void updateState() {
		
		// Movement
		if (moveLeft) {	// Left
			setX(getX() + (getVx() * MovingObject.DIRECTION_LEFT));
		}
		if (moveRight) { // Right
			setX(getX() + (getVx() * MovingObject.DIRECTION_RIGHT));
		}
		if (moveUp) { // Up
			setY(getY() + (getVy() * MovingObject.DIRECTION_UP));
		}
		if (moveDown) {	// Down
			setY(getY() + (getVy() * MovingObject.DIRECTION_DOWN));
		}
		
		// Size of the asteroid when hit
		if (isHit()) {
			float damage = previousLife - getLife();
			float shrinkPercentage = (INITIAL_LIFE/damage-1)/(INITIAL_LIFE/damage);
			
			// Shrink the bitmap
			setBitmap(0, Bitmap.createScaledBitmap(getBitmap(), Math.round(shrinkPercentage*(getRect().width())), Math.round(shrinkPercentage*(getRect().height())), true));			
			
			float newDamage = shrinkPercentage*getDamage();
			

			setDamage(newDamage);
			
			setHit(false);
		}
		
		// Calls the superclass method that updates the rectangle automatically.
		super.updateState(); 
	}
	
	@Override
	public void setLife(float life) {
		previousLife = getLife();
		super.setLife(life);
	}
	
	@Override
	public String toString() {
		return "ASTEROID";
	}

}
