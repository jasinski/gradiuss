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
	private float life;
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
		this.isAlive = isAlive;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void setLife(float life) {
		this.life = life;
	}
	
	public float getLife() {
		return life;
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
			setX((int) (getX() - (getVx() * getDirectionX()) ));
		}
		if (moveRight) {	
			setX((int) (getX() + (getVx() * getDirectionX()) ));
		}
		if (moveUp) {	
			setY((int) (getY() - (getVy() * getDirectionY()) ));
		}
		if (moveDown) {	
			setY((int) (getY() + (getVy() * getDirectionY()) ));
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
