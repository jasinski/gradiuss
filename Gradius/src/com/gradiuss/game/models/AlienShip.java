package com.gradiuss.game.models;

import android.graphics.Bitmap;

public class AlienShip extends Enemy {
	private static final String TAG = AlienShip.class.getSimpleName();

	
	public AlienShip(Bitmap bitmap, int x, int y) {
		super(bitmap, x, y);
	}

	private boolean shooting;
	
	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}
	
	public boolean isShooting() {
		return shooting;
	}

	@Override
	public void updateState() {
		super.updateState();
	}

}
