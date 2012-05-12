package com.gradiuss.game.models;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

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
		if (moveLeft) {	
			setX(getX() + (getVx() * MovingObject.DIRECTION_LEFT));
		}
		if (moveRight) {	
			setX(getX() + (getVx() * MovingObject.DIRECTION_RIGHT));
		}
		if (moveUp) {	
			setY(getY() + (getVy() * MovingObject.DIRECTION_UP));
		}
		if (moveDown) {	
			setY(getY() + (getVy() * MovingObject.DIRECTION_DOWN));
		}
		
		// Size of the asteroid when hit
		if (isHit()) {
			float damage = previousLife - getLife();
			float shrinkPercentage = (INITIAL_LIFE/damage-1)/(INITIAL_LIFE/damage);
			Log.d(TAG, "ASTEROID TEST: shrinkPercentage = " + shrinkPercentage);
//			setBitmap(0, Bitmap.createBitmap(getBitmap(), 0, 0, Math.round(shrinkPercentage*getBitmap().getWidth()), Math.round(shrinkPercentage*getBitmap().getHeight())));
			setBitmap(0, Bitmap.createScaledBitmap(getBitmap(), Math.round(shrinkPercentage*(getRect().width())), Math.round(shrinkPercentage*(getRect().height())), true));			
			
			float newDamage = shrinkPercentage*getDamage();
			Log.d(TAG, "newDamage=" + newDamage);
			
			setDamage((int)(newDamage));
			Log.d(TAG, "damage=" + getDamage());
			
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
