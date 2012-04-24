package com.gradiuss.game.models;

import android.graphics.Bitmap;

public class Asteroid extends Enemy {
	
	public Asteroid(Bitmap bitmap, int x, int y) {
		super(bitmap, x, y);
		super.setVisible(true);
	}

	@Override
	public void updateState() {
		//setting the new position
		setY((int)getY()+2);
		setX((int)getX()+1);
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
