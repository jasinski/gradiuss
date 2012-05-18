package com.gradiuss.game.models;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;

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
	
	/**
	 * Adding a new parallax background.
	 * @param bitmap
	 * @param movementSpeed
	 */
	public void addBackground(Bitmap bitmap, float movementSpeed) {
		
		// Calculating the number of images needed to fill the screen
		int nrOfBgImages = calculateBgImages(bitmap.getHeight());
		
		// Array with size = nrOfBgImages
		Background[] bgArray = new Background[nrOfBgImages];
		
		// Filling the array with Background-objects
		for (int i = 0; i < bgArray.length; i++) {
			bgArray[i] = new Background(bitmap, movementSpeed, nrOfBgImages);
			bgArray[i].setBitmap(bitmap);
			bgArray[i].setMovementSpeed(movementSpeed);
			bgArray[i].setX(screenWidth/2);
			bgArray[i].setY(screenHeight - i*bgArray[i].getBitmap().getHeight());
		}
		bitmaps.add(bgArray);
	}
	
	/**
	 * Remove a background.
	 * @param position
	 */
	public void removeBackground(int position) {
		bitmaps.remove(position);
	}
	
	public void setSpeed(int position) {
		
	}
	
	// Calculate how many background images are needed to cover the whole screen.
	private int calculateBgImages(int bitmapHeight) {
		return (int) Math.ceil(screenHeight/bitmapHeight) + 1;
	}
	
	// Scroll the backgrounds
	@Override
	public void updateState() {
		for (Background[] bgArray : bitmaps) {
			for (Background background : bgArray) {
				background.setY(background.getY() + background.getMovementSpeed());
				
				
				if (background.getY() >= 2*screenHeight) {
					background.setY((int) background.getY() - background.getRedrawPosition());
				}
			}
		}
		
	}
	
	// Draw the backgrounds
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
