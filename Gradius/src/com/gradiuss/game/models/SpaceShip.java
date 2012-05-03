package com.gradiuss.game.models;


import android.graphics.Bitmap;

public class SpaceShip extends MovingObject {

	private boolean isAlive;
	private int life = 100;
	private boolean shooting;
	
	public SpaceShip(Bitmap bitmap, float x, float y) {
		super(bitmap, x, y);
		setVisible(true);
	}
	
	public SpaceShip(Bitmap bitmap, float x, float y, float xSpeed, float ySpeed) {
		super(bitmap, x, y);
		this.setVx(xSpeed);
		this.setVy(ySpeed);
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

		// Calls the superclass method that updates the rectangle automatically.
		super.updateState(); 
	}
	

}
