package com.gradiuss.game.models;

import android.graphics.Bitmap;
import android.graphics.Rect;

public abstract class Enemy extends MovingObject {
	private static final String TAG = Enemy.class.getSimpleName();
	private boolean isAlive;
	private float life;
	private boolean hit;
	private float damage;
	public int SCORE = 0;
	
	public Enemy(Bitmap bitmap, float x, float y) {
		super(bitmap, x, y);
	}
	
	public Enemy() {
		
	}
	
	public Enemy(Bitmap bitmap, float x, float y, Rect rectangle) {
		super(bitmap, x, y, rectangle);
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
	
	public void setHit(boolean hit) {
		this.hit = hit;
	}
	
	public boolean isHit() {
		return hit;
	}
	
	public void setDamage(float damage) {
		this.damage = damage;
	}
	
	public float getDamage() {
		return damage;
	}

}
