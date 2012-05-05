package com.gradiuss.game.models;

import java.util.List;

import com.gradiuss.game.GameViewActivity;
import com.gradiuss.game.R;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.MediaPlayer;


public class Explosion extends GameObject {
	private static final String TAG = Explosion.class.getSimpleName();
	public static final float STANDARD_FRAME_TIME = 50000000; //Measure for how long time frame should be shown
	public long previousExplosionFrame = 0; //Measures the last time the picture of a explosion-state changed
	
	public Explosion(List<Bitmap> animations, float x, float y, Rect rectangle) throws IllegalArgumentException {
		super(animations, x, y, rectangle);
	}
	
	public void updateState() {
		nextAnimation();
		super.updateState();
	}
		
	public boolean lastFrame() {
		return getAnimationPointer() == getAnimations().size()-1;
		   //Sound of explosion
	}
	
}
