package com.gradiuss.game.models;

import android.graphics.Bitmap;
import android.graphics.Rect;

public abstract class Projectile extends MovingObject {
	
	public static final float FIRE_TIME_STANDARD = 100000000;
	
	private int damage = 0;
	private float fireInterval = FIRE_TIME_STANDARD;

	public Projectile(Bitmap bitmap, float x, float y) {
		super(bitmap, x, y);
	}
	
	public Projectile(Bitmap bitmap, float x, float y, int damage) {
		super(bitmap, x, y);
		this.damage = damage;
	}
	
	public Projectile(Bitmap bitmap, float x, float y, Rect rectangle) {
		super(bitmap, x, y, rectangle);
	}
	
	public Projectile(Bitmap bitmap, float x, float y, Rect rectangle, int damage) {
		super(bitmap, x, y, rectangle);
		this.damage = damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setFireInterval(float fireInterval) {
		this.fireInterval = fireInterval;
	}
	
	public float getFireInterval() {
		return fireInterval;
	}

}
