package com.gradiuss.game.models;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

public abstract class GameObject {
	
	private float x; // The X coordinate
	private float y; // The Y coordinate
	private List<Bitmap> bitmaps; // The animations
	private int animationPointer = 0;
	private Rect rectangle;
	private boolean visible;
	
	public GameObject(Bitmap bitmap, float x, float y, Rect rectangle) throws IllegalArgumentException {
		if (rectangle == null) {
			throw new IllegalArgumentException();
		}
		this.bitmaps = new ArrayList<Bitmap>(1);
		this.bitmaps.add(bitmap);
		this.x = x;
		this.y = y;
		this.rectangle = rectangle;
	}
	
	public GameObject(List<Bitmap> bitmaps, float x, float y, Rect rectangle) throws IllegalArgumentException {
		if (rectangle == null) {
			throw new IllegalArgumentException();
		}
		//scale the bitmaps according to the specification of the rectangle if the passed bitmaps are not empty
		List<Bitmap> scaledBitmaps = new ArrayList<Bitmap>();
		for(Bitmap bm : bitmaps) {
			scaledBitmaps.add(Bitmap.createScaledBitmap(bm, rectangle.width(), rectangle.height(), true));
		}
		this.bitmaps = scaledBitmaps;
		this.x = x;
		this.y = y;
		this.rectangle = rectangle;
	}

	public GameObject(Bitmap bitmap, float x, float y) throws IllegalArgumentException {
		this(bitmap, x, y, new Rect((int) x - bitmap.getWidth()/2, (int) y - bitmap.getHeight()/2, (int) x + bitmap.getWidth()/2, (int) y + bitmap.getHeight()/2));
	}
	
	public GameObject() {
		
	}

	public void setX(float x) {
		this.x = x;
	}
	
	public float getX() {
		return x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getY() {
		return y;
	}
	
	public int getBitmapHeight() {
		return bitmaps.get(0).getHeight();
	}
	
	public int getBitmapWidth() {
		return bitmaps.get(0).getWidth();
	}
	
	public int getRectWidth() {
		return rectangle.right - rectangle.left;
	}
	
	public int getRectHeight() {
		return rectangle.bottom - rectangle.top;
	}
	
	public boolean collisionDetection(GameObject gameobject) {
		return rectangle.intersect(gameobject.rectangle);
	}
	
	public void setBitmap(int position, Bitmap bitmap) {
		this.bitmaps.set(position, bitmap);
	}
	
	public Bitmap getBitmap() {
		return bitmaps.get(animationPointer);
	}
	
	public Bitmap getBitmap(int position) {
		return bitmaps.get(position);
	}
	
	public void setAnimations(List<Bitmap> animations) {
		this.bitmaps = animations;
	}
	
	public List<Bitmap> getAnimations() {
		return bitmaps;
	}
	
	public void setAnimationPointer(int animationPointer) {
		this.animationPointer = animationPointer;
	}
	
	public int getAnimationPointer() {
		return animationPointer;
	}
	
	public void nextAnimation() {
		if (animationPointer < bitmaps.size()-1) {
			this.animationPointer++;
		}
	}
	
	public void previousAnimation() {
		if (animationPointer > 0) {
			this.animationPointer--;
		}
	}

	public void addAnimation(Bitmap animation) {
		bitmaps.add(animation);
	}
	
	public void setRect(Rect rectangle) {
		this.rectangle = rectangle;
	}
	
	public Rect getRect() {
		return rectangle;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
//	 This method should be overridden by the extending class. 
//	 The overriding method should call "super.updateState()" at the end of the method 
//	 to automatically update the rectangle to fit the size of the bitmap.
//	 Otherwise the updating of the rectangle should be implemented explicitly.
	public void updateState() {
		
		// Update rectangle
		getRect().set((int) getX() - getBitmapWidth()/2, (int) getY() - getBitmapHeight()/2, (int) getX() + getBitmapWidth()/2, (int) (getY()) + getBitmapHeight()/2);
	}
	
	// Paint the new image with the middle at the coordinates and not the edge
	public void draw(Canvas canvas) {
		
		// Draw the bitmap if the object is set to be visible
		if (visible) {
			// TODO: TEMPORARY "get(0)"
			canvas.drawBitmap(bitmaps.get(animationPointer), x - getRectWidth()/2, y - getRectHeight()/2, null);
			//canvas.drawBitmap(bitmaps.get(animationPointer), getRect(), getRect(), null);

			// TODO - TEMPORARY: paint the rectangle green, just for testing 
			Paint paint = new Paint();
			paint.setColor(Color.GREEN);
			paint.setStyle(Style.STROKE);
			canvas.drawRect(rectangle, paint);
			// TODO - TEMPORARY: paint the rectangle green, just for testing 
			
		}
	}
	
}