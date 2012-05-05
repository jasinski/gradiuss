package com.gradiuss.game.models;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class TypeOneProjectile extends Projectile {
	private static final String TAG = TypeOneProjectile.class.getSimpleName();
	
	public TypeOneProjectile(Bitmap bitmap, float x, float y) {
		super(bitmap, x, y);
	}
	
	public TypeOneProjectile(Bitmap bitmap, float x, float y, Rect rectangle) {
		super(bitmap, x, y, rectangle);
	}
	
	public TypeOneProjectile(Bitmap bitmap, float x, float y, float damage) {
		super(bitmap, x, y, damage);
	}
	
	public TypeOneProjectile(Bitmap bitmap, float x, float y, Rect rectangle, float damage) {
		super(bitmap, x, y, rectangle, damage);
	}
	
	public TypeOneProjectile(Projectile projectile) {
		super(projectile.getBitmap(), projectile.getX(), projectile.getY());
		this.setDamage(projectile.getDamage());
		this.setFireInterval(projectile.getFireInterval());	
		this.setVisible(projectile.isVisible());
		this.setMoveUp(projectile.isMovingUp());
		this.setVy(projectile.getVy());
		this.setFireInterval(projectile.getFireInterval());
	}

	@Override
	public void updateState() {
		
		// Movement upwards
		if (moveUp) {	
			setY((int) (getY() - (getVy() * getDirectionY()) ));
		}
		
		// Destroy if touches upper screen boundary
		if (getY() < 0) {
			setVisible(false);
		}
		
		// Calls the superclass method that updates the rectangle automatically.
		super.updateState(); 
	}

}
