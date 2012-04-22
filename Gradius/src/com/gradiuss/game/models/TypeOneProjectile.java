package com.gradiuss.game.models;

import android.graphics.Bitmap;

public class TypeOneProjectile extends Projectile {

	public TypeOneProjectile(Bitmap bitmap, float x, float y) {
		super(bitmap, x, y);
	}

	@Override
	public void updateState() {
		
		// Movement upwards
		if (moveUp) {	
			setY((int) (getY() - (getVy() * getDirectionY()) ));
		}
		
		// Destroy if touches upper screen boundary
		if (this.getY() < 0) {
			setVisible(false);
		}
		
		// Update rectangle
		getRectangle().set((int) getX() - getWidth()/2, (int) getY() - getHeight()/2, (int) getX() + getWidth()/2, (int) (getY()) + getHeight()/2);
	}

}
