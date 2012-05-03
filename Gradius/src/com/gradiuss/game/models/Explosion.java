package com.gradiuss.game.models;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Explosion extends GameObject {


	public static final float STANDARD_FRAME_TIME = 50000000; //Measure for how long time frame should be shown
	public long previousExplosionFrame = 0; //Measures the last time the picture of a explosion-state changed
	
	public Explosion() {} //for testing;
	
	public Explosion(List<Bitmap> animations, float x, float y, Rect rectangle) throws IllegalArgumentException {
		super(animations, x, y, rectangle);
	}
	
	public void updateState() {
		
		nextAnimation();
		super.updateState();
	}
	
	public boolean lastFrame() {
		return getAnimationPointer() == getAnimations().size()-1;
	}
	
}
