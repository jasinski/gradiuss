package com.gradiuss.game.models;


import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

public class SpaceShip extends MovingObject {
	private static final String TAG = SpaceShip.class.getSimpleName();
	private boolean isAlive;
	private int life = 100;
	private boolean shooting;
	private boolean hit;
	int counter;
	
	public SpaceShip(Bitmap bitmap, float x, float y) {
		super(bitmap, x, y);
		setVisible(true);
	}
	
	public SpaceShip(Bitmap bitmap, float x, float y, float xSpeed, float ySpeed) {
		super(bitmap, x, y);
		this.setVx(xSpeed);
		this.setVy(ySpeed);
	}
	
	public SpaceShip(List<Bitmap> bitmaps, float x, float y, Rect rectangle) {
		super(bitmaps, x, y, rectangle);
	}
	
	public SpaceShip(List<Bitmap> bitmaps, float x, float y) {
		super(bitmaps, x, y);
	}
	
	public void setAlive(boolean isAlive) {
		this.isAlive = life > 0 ? isAlive : false;	
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void setLife(int life) {
		this.life = life;
	}
	
	public int getLife() {
		return (life > 100) ? 100 : life;
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
		// Update movement
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
		
		// Spaceship is hit
		if (hit) {
			setAnimationPointer(1);
			counter = 0;
			hit = false;
		}
		
		if (getAnimationPointer() == 1) {
			if (counter == 2) {
				setAnimationPointer(0);
			} else {
				counter++;
			}
		}

		// Calls the superclass method that updates the rectangle automatically.
		super.updateState(); 
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
	

}
