package com.gradiuss.game.models;

import android.graphics.Bitmap;

public abstract class Projectile extends MovingObject {

	public Projectile(Bitmap bitmap, int x, int y) {
		super(bitmap, x, y);
	}

}
