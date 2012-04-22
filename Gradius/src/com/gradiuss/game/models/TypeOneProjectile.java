package com.gradiuss.game.models;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class TypeOneProjectile extends Projectile {

	public TypeOneProjectile(Bitmap bitmap, float x, float y) {
		super(bitmap, x, y);
	}
	
	public TypeOneProjectile(Bitmap bitmap, float x, float y, Rect rectangle) {
		super(bitmap, x, y, rectangle);
	}
	
	public TypeOneProjectile(Bitmap bitmap, float x, float y, int damage) {
		super(bitmap, x, y, damage);
	}
	
	public TypeOneProjectile(Bitmap bitmap, float x, float y, Rect rectangle, int damage) {
		super(bitmap, x, y, rectangle, damage);
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
