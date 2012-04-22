package com.gradiuss.game.models;


import android.graphics.Bitmap;

public class SpaceShip extends MovingObject {

	private boolean isAlive;
	private int life;
	private boolean shooting;
	
	public SpaceShip(Bitmap bitmap, int x, int y) {
		super(bitmap, x, y);
	}
	
	public SpaceShip(Bitmap bitmap, float x, float y, float xSpeed, float ySpeed) {
		super(bitmap, x, y);
		this.setVx(xSpeed);
		this.setVy(ySpeed);
	}
	
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void setLife(int life) {
		this.life = life;
	}
	
	public int getLife() {
		return life;
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
		
		// Update rectangle (LÄGG DETTA I GAMEOBJECTS UPDATESTATE METOD SEN!)
		getRectangle().set((int) getX() - getWidth()/2, (int) getY() - getHeight()/2, (int) getX() + getWidth()/2, (int) (getY()) + getHeight()/2);
	}

}
