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
	private final float starting_bar = 100;
	private float lifeBar = 100;
	private boolean hit = false;
	private float initialBitmapWidth;
	private float damage;

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
	
	public void setLifeBar(float lifeBar) {
		this.lifeBar = lifeBar;
	}
	
	public float getLifeBar() {
		return lifeBar;
	}
	
	@Override
	public void initialize() {
		getRect().set((int) getX(), (int) getY(), (int) (getX() + initialBitmapWidth), (int) (getY()) + getBitmapHeight());
	}

	@Override
	public void updateState() {
		
		if (hit) {
			damage = (starting_bar - (starting_bar - lifeBar))/starting_bar;
//			if (damage <= 0) {
//				damage = 0.01f;
//			}
			int newWidth = (int) (damage*((float) initialBitmapWidth));
			if (newWidth < 1) {
				newWidth = 1;
			}
			// TODO - REMOVE LOGGING
			Log.d(TAG, "damage=" + damage);
			
			setBitmap(0, Bitmap.createScaledBitmap(getBitmap(), newWidth, (int) getBitmapHeight(), true));
			
			hit = false;
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		// Draw the bitmap if the object is set to be visible
		if (isVisible()) {
			// TODO: TEMPORARY "get(0)"
			canvas.drawBitmap(getAnimations().get(0), getX()/* - getRectWidth()/2*/, getY()/* - getRectHeight()/2*/, null);
			canvas.drawBitmap(getAnimations().get(1), getX()/* - getRectWidth()/2*/, getY()/* - getRectHeight()/2*/, null);

			//canvas.drawBitmap(bitmaps.get(animationPointer), getRect(), getRect(), null);

			// TODO - TEMPORARY: paint the rectangle green, just for collision-testing 
			Paint paint = new Paint();
			paint.setColor(Color.GREEN);
			paint.setStyle(Style.STROKE);
			canvas.drawRect(getRect(), paint);
			
		}
	}
	
	

}
