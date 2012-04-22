package com.gradiuss.game.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

public abstract class GameObject {
	
	private float x; // The X coordinate
	private float y; // The Y coordinate
	private Bitmap bitmap; // The bitmap
	private int width;
	private int height;
	private Rect rectangle;
	private boolean visible;
	
	public GameObject(Bitmap bitmap, float x, float y, Rect rectangle) throws IllegalArgumentException {
		if (rectangle == null) {
			throw new IllegalArgumentException();
		}
		this.bitmap = bitmap;
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		this.x = x;
		this.y = y;
		this.rectangle = rectangle;
	}

	public GameObject(Bitmap bitmap, float x, float y) throws IllegalArgumentException {
		this(bitmap, x, y, new Rect((int) x - bitmap.getWidth()/2, (int) y - bitmap.getHeight()/2, (int) x + bitmap.getWidth()/2, (int) y + bitmap.getHeight()/2));
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
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setRectangle(Rect rectangle) {
		this.rectangle = rectangle;
	}
	
	public Rect getRectangle() {
		return rectangle;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return visible;
	}

	// This is implemented by the extending class
	public abstract void updateState();
	
	// Paint the new image with the middle at the coordinates and not the edge.
	public void draw(Canvas canvas) {
		if (visible) {
			//canvas.drawBitmap(bitmap, x - (getWidth()/2), y - (getHeight()/2), null);
			canvas.drawBitmap(bitmap, null, rectangle, null);
			Matrix m = new Matrix();
			m.setRotate(10);
			
			//canvas.drawBitmap(bitmap, m, null);
			
			// Temporary: paint the rectangle green, just for testing
			Paint paint = new Paint();
			paint.setColor(Color.GREEN);
			paint.setStyle(Style.STROKE);
			canvas.drawRect(rectangle, paint);
			// Temporary: paint the rectangle green, just for testing
		}
	}
	
}