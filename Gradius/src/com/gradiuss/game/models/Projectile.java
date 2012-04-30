package com.gradiuss.game.models;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Projectile extends MovingObject {
	
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
	
	public Projectile(Projectile projectile) {
		super(projectile.getBitmap(), projectile.getX(), projectile.getY());
		this.damage = projectile.damage;
		this.fireInterval = projectile.fireInterval;	
		this.setVisible(projectile.isVisible());
		this.setMoveUp(projectile.isMovingUp());
		this.setVy(projectile.getVy());
		this.setFireInterval(projectile.getFireInterval());
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
