package com.gradiuss.game.models;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;

public class LifeBar extends GameObject {
	private static final String TAG = LifeBar.class.getSimpleName();
	private final float startingLifeBar = 100;
	private float life = startingLifeBar;
//	private float previousLifeBar = startingLifeBar;
	private boolean hit = false;
	private float initialBitmapWidth;
	private float newWidth;
	private boolean dead;

	public LifeBar(Bitmap bitmap, float x, float y, Rect rectangle) throws IllegalArgumentException {
		super(bitmap, x, y, rectangle);
		initialBitmapWidth = bitmap.getWidth();
		initialize();
	}
	
	public LifeBar(Bitmap bitmap, float x, float y) throws IllegalArgumentException {
		super(bitmap, x, y);
		initialBitmapWidth = bitmap.getWidth();
		initialize();
	}
	
	public LifeBar(List<Bitmap> bitmaps, float x, float y) throws IllegalArgumentException {
		super(bitmaps, x, y);
		initialBitmapWidth = bitmaps.get(0).getWidth();
		initialize();
	}
	
	public void setHit(boolean hit) {
		this.hit  = hit;
	}
	
	public boolean isHit() {
		return hit;
	}
	
	public void setLifeBar(float life) {
		this.life = life;
	}
	
	public float getLifeBar() {
		return life;
	}
	
	public void decreaseLife(float life) {
		this.life = this.life - life;
	}
	
	public void increaseLife(float life) {
		this.life = this.life + life;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	@Override
	public void initialize() {
		newWidth = initialBitmapWidth;
		getRect().set((int) getX(), (int) getY(), (int) (getX() + newWidth), (int) (getY()) + getBitmapHeight());
	}

	@Override
	public void updateState() {
		if (hit) {
			newWidth = initialBitmapWidth * (life/startingLifeBar);	
			if (newWidth <= 1) {
				newWidth = 1;
			}
			hit = false;
		}
		
		if (life <= 0) {
			dead = true;
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		// Draw the bitmap if the object is set to be visible
		if (isVisible()) {
			// TODO: TEMPORARY "get(0)"
			canvas.drawBitmap(Bitmap.createScaledBitmap(getAnimations().get(0), (int) newWidth, (int) getBitmapHeight(), true), getX(), getY(), null);
			canvas.drawBitmap(getAnimations().get(1), getX(), getY(), null);

			// TODO - TEMPORARY: paint the rectangle green, just for testing 
			Paint paint = new Paint();
			paint.setColor(Color.GREEN);
			paint.setStyle(Style.STROKE);
			canvas.drawRect(getRect(), paint);
			
		}
	}
	
	

}
