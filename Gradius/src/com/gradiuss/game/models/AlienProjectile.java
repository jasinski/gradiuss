package com.gradiuss.game.models;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class AlienProjectile extends Projectile {
	
	public AlienProjectile(Bitmap bitmap, float x, float y) {
		super(bitmap, x, y);
	}
	public AlienProjectile(Bitmap bitmap, float x, float y, Rect rectangle) {
		super(bitmap, x, y, rectangle);
	}
	
	public AlienProjectile(Bitmap bitmap, float x, float y, float damage) {
		super(bitmap, x, y, damage);
	}
	
	public AlienProjectile(Bitmap bitmap, float x, float y, Rect rectangle, float damage) {
		super(bitmap, x, y, rectangle, damage);
	}
	
	public AlienProjectile(Projectile projectile) {
		super(projectile.getBitmap(), projectile.getX(), projectile.getY());
//		this.setRect(projectile.getRect());
		this.setDamage(projectile.getDamage());
		this.setFireInterval(projectile.getFireInterval());	
		this.setVisible(projectile.isVisible());
		this.setMoveDown(projectile.isMovingDown());
		this.setVy(projectile.getVy());
		this.setFireInterval(projectile.getFireInterval());
	}
	
	
	@Override
	public void updateState() {
		
		// MovemenSt upwards
		if (moveDown) {	
			setY(getY() + getVy() * MovingObject.DIRECTION_UP);
		}
		
		// Destroy if touches upper screen boundary
		if (getY() < 0) {
			setVisible(false);
		}
		
		// Calls the superclass method that updates the rectangle automatically.
		super.updateState(); 
	}


}
