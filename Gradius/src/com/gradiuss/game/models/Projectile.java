package com.gradiuss.game.models;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Rect;

public abstract class Projectile extends MovingObject {
	
	private static final String TAG = Projectile.class.getSimpleName();
	public static final float FIRE_TIME_STANDARD = 100000000;
	private float damage = 0;
	private float fireInterval = FIRE_TIME_STANDARD;
	protected int windowheight;

	public Projectile(Bitmap bitmap, float x, float y) {
		super(bitmap, x, y);
	}
	
	public Projectile(Bitmap bitmap, float x, float y, float damage) {
		super(bitmap, x, y);
		this.damage = damage;
	}
	
	public Projectile(Bitmap bitmap, float x, float y, Rect rectangle) {
		super(bitmap, x, y, rectangle);
	}
	
	public Projectile(Bitmap bitmap, float x, float y, Rect rectangle, float damage) {
		super(bitmap, x, y, rectangle);
		this.damage = damage;
	}
	
	public Projectile() {
		setAnimations(new ArrayList<Bitmap>(1));
	}
	
	public Projectile(Projectile projectile) {
		super(projectile.getBitmap(), projectile.getX(), projectile.getY());
		this.damage = projectile.damage;
		this.fireInterval = projectile.fireInterval;	
		this.setVisible(projectile.isVisible());
		this.setMoveUp(projectile.isMovingUp());
		this.setVy(projectile.getVy());
		this.setFireInterval(projectile.getFireInterval());
	}
	
	public void setDamage(float damage) {
		this.damage = damage;
	}
	
	public float getDamage() {
		return damage;
	}
	
	public void setFireInterval(float fireInterval) {
		this.fireInterval = fireInterval;
	}
	
	public float getFireInterval() {
		return fireInterval;
	}
	
	public void setWindowHeight(int height) {
		this.windowheight = height;
	}

}
