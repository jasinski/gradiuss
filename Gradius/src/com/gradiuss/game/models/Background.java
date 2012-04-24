package com.gradiuss.game.models;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;


public class Background extends MovingObject {

	int screenWidth;
	int screenHeight;
	
	// TODO - TEMPORARY VARIABLE
	int tmp = 1;
	
	public Background(Bitmap bitmap, float x, float y, Rect rectangle, int screenWidth, int screenHeight) {
		super(bitmap, x, y, rectangle);
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	@Override
	public void updateState() {
		
		// The int variable makes the update of the background to slow down
		if (moveDown && tmp % 5 == 0) {
			setY((int) (getY() + (getVy() * getDirectionY()) ));
			tmp = 1;
			Log.d("TEST", "getY() = " + getY() + ", screenHeight = " + screenHeight);
		} else {
			tmp++;
		}
		
		// TODO - NEED TO BE UPDATED: Doesn't draw the image at the right spot
		if (getY() >= screenHeight + getHeight()/2) {
			setY(-getHeight());
		}
		
		super.updateState();
	}

}
