package com.gradiuss.game.models;

import android.graphics.Bitmap;

public class Background {
	private static final String TAG = Background.class.getSimpleName();
	private float x;
	private float y;
	private Bitmap bitmap;
	private float movementSpeed;
	private float redrawPosition;
	
	public Background(Bitmap bitmap, float movementSpeed, int nrOfBackgroundCopies) {
		this.bitmap = bitmap;
		this.movementSpeed = movementSpeed;
		this.redrawPosition = nrOfBackgroundCopies * bitmap.getHeight();
	}
	
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getX() {
		return x;
	}
	
	public void setMovementSpeed(float movementSpeed) {
		this.movementSpeed = movementSpeed;
	}
	
	public float getMovementSpeed() {
		return movementSpeed;
	}
	
	public void setRedrawPosition(float redrawPosition) {
		this.redrawPosition = redrawPosition;
	}
	
	public float getRedrawPosition() {
		return redrawPosition;
	}
	
}
