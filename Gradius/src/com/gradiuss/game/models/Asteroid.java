package com.gradiuss.game.models;

import android.graphics.Bitmap;

public class Asteroid extends Enemy {
	
	public Asteroid(Bitmap bitmap, int x, int y) {
		super(bitmap, x, y);
	}

	@Override
	public void updateState() {
		
//		if (moveLeft) {	
//			setX((int) (getX() - (getVx() * getDirectionX()) ));
//		}
//		if (moveRight) {	
//			setX((int) (getX() + (getVx() * getDirectionX()) ));
//		}
//		if (moveUp) {	
//			setY((int) (getY() - (getVy() * getDirectionY()) ));
//		}
//		if (moveDown) {	
//			setY((int) (getY() + (getVy() * getDirectionY()) ));
//		}
	}

	
	@Override
	public void setAlive(boolean isAlive) {
		super.setAlive(isAlive);
	}

	
	@Override
	public boolean isAlive() {
		return super.isAlive();
	}

	
	@Override
	public int getLife() {
		return super.getLife();
	}

	

}
