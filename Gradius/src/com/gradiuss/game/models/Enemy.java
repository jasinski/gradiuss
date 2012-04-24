package com.gradiuss.game.models;

import android.graphics.Bitmap;

public abstract class Enemy extends MovingObject {
	
	public Enemy(Bitmap bitmap, int x, int y) {
		super(bitmap, x, y);
	}

	private boolean isAlive;
	private int life;
	
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

}
