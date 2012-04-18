package com.gradiuss.game.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class GameObject {
	
	private float x; // The X coordinate
	private float y; // The Y coordinate
	private Bitmap bitmap; // The bitmap
	private boolean visible;

	public GameObject(Bitmap bitmap, float x, float y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public float getX() {
		return x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public float getY() {
		return y;
	}
	
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return visible;
	}

	// This is implemented by the extending class
	public abstract void updateState();
	
	public void draw(Canvas canvas) {
		// Paint the new image with the middle at the coordinates and not the edge.
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth()/2), y - (bitmap.getHeight()/2), null);
	}
	
}