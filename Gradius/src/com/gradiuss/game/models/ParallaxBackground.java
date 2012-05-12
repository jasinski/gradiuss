package com.gradiuss.game.models;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class ParallaxBackground extends MovingObject {
	private static final String TAG = ParallaxBackground.class.getSimpleName();
	private List<Background[]> bitmaps;
	private float[] speeds;
	private float screenHeight;
	private float screenWidth;
	int nrOfBackgroundCopies;
	
	int tmp = 1;
	
	public ParallaxBackground(float screenHeight, float screenWidth) throws IllegalArgumentException {
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		setVisible(true);
		bitmaps = new ArrayList<Background[]>();
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
	
	public float getScreenHeight() {
		return this.screenHeight;
	}
	
	public void addBackground(Bitmap bitmap, float movementSpeed) {
		int nrOfBgImages = calculateBgImages(bitmap.getHeight());
		Background[] bgArray = new Background[nrOfBgImages];
		for (int i = 0; i < bgArray.length; i++) {
			bgArray[i] = new Background(bitmap, movementSpeed, nrOfBgImages);
			bgArray[i].setBitmap(bitmap);
			bgArray[i].setMovementSpeed(movementSpeed);
			bgArray[i].setX(screenWidth/2);
			bgArray[i].setY(screenHeight - i*bgArray[i].getBitmap().getHeight());
		}
		bitmaps.add(bgArray);
	}
	
	public void removeBackground(int position) {
		bitmaps.remove(position);
	}
	
	public void setSpeed(int position) {
		
	}
	
	private int calculateBgImages(int bitmapHeight) {
		// Calculate how many background images are needed to cover the whole screen
		return (int) Math.ceil(screenHeight/bitmapHeight) + 1;
	}
	
//	public void initParallaxBackground() {
//		for (Background[] bgArray : bitmaps) {
//			for (int i = 0; i < bgArray.length; i++) {
//				bgArray[i].setX(screenWidth/2);
//				bgArray[i].setY(screenHeight - bgArray[i].getBitmap().getHeight()/2 - i*bgArray[i].getBitmap().getHeight());
//			}
//		}
//	}
	
	
	@Override
	public void updateState() {
		for (Background[] bgArray : bitmaps) {
			for (Background background : bgArray) {
				background.setY(background.getY() + background.getMovementSpeed());
				
//				Log.d(TAG, "screen height(ParallaxBg) = " + screenHeight + ", test = " + background.getBitmap().getHeight()/2);
				
				if (background.getY() >= 2*screenHeight/* + background.getBitmap().getHeight()/2*/) {
					background.setY((int) background.getY() - background.getRedrawPosition());
				}
			}
		}
		
	}
	
	public void draw(Canvas canvas) {
		// Draw the bitmap if the object is set to be visible
		for (Background[] bgArray : bitmaps) {
			for (Background background : bgArray) {
				if (isVisible()) {
					canvas.drawBitmap(background.getBitmap(), background.getX() - background.getBitmap().getWidth()/2,  background.getY() - background.getBitmap().getHeight(), null);
				}
			}
		}
		
	}
	
	
	
}
