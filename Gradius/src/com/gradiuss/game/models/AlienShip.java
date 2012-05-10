package com.gradiuss.game.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class AlienShip extends Enemy {
	private static final String TAG = AlienShip.class.getSimpleName();
	private boolean shooting;
	private boolean hit;
	private SpaceShip target;
	
	public AlienShip(Bitmap bitmap, float x, float y, SpaceShip spacesh) {
		super(bitmap, x, y);
		this.target = spacesh;
	}

	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}
	
	public boolean isShooting() {
		return shooting;
	}
	
	public void setHit(boolean hit) {
		this.hit = hit;
	}
	
	public boolean isHit() {
		return hit;
	}

	@Override
	public void updateState() {
		if(target.moveDown)
			setMoveDown(true);
		if(target.moveUp)
			setMoveUp(true);
		if(target.moveLeft)
			setMoveLeft(true);
		if(target.moveRight)
			setMoveRight(true);
		if (moveLeft) {	
			setX((getX() + (getVx() * MovingObject.DIRECTION_LEFT)));
		}
		if (moveRight) {	
			setX((getX() + (getVx() * MovingObject.DIRECTION_RIGHT)));
		}
		if (moveUp) {	
			setY((getY() + (getVy() * MovingObject.DIRECTION_UP)));
		}
		if (moveDown) {	
			setY((getY() + (getVy() * MovingObject.DIRECTION_DOWN)));
		}
		super.updateState();
	}
	

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}

}
