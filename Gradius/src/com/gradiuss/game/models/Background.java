package com.gradiuss.game.models;

import android.graphics.Bitmap;

public class Background {
	private static final String TAG = Background.class.getSimpleName();
	private float x;
	private float y;
	private Bitmap bitmap;
	private int movementSpeed;
	private float redrawPosition;
	
	public Background(Bitmap bitmap, int movementSpeed, int nrOfBackgroundCopies) {
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
	
	public void setMovementSpeed(int movementSpeed) {
		this.movementSpeed = movementSpeed;
	}
	
	public int getMovementSpeed() {
		return movementSpeed;
	}
	
	public void setRedrawPosition(float redrawPosition) {
		this.redrawPosition = redrawPosition;
	}
	
	public float getRedrawPosition() {
		return redrawPosition;
	}
	
}
