package com.gradiuss.game.models;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Explosion extends GameObject{
	Bitmap[] bitmaps = new Bitmap[9];
	private int bitmapNr = 0;
	public static final float STANDARD_FRAME_TIME = 50000000; //Measure for how long time frame should be shown
	public long previousExplosionFrame = 0; //Measures the last time the picture of a explosion-state changed
	
	public Explosion(float x, float y, Rect rect) {
		super(null, x, y, rect);	 
	}
	
	public void updateState() {
		setBitmap(bitmaps[bitmapNr]);
		if(bitmapNr < 8) {
			bitmapNr++;
		}
		super.updateState();
	}
	
	public boolean lastFrame() {
		return bitmapNr == 8;
	}
	
	//set the explosion frames with resources from GameView
	public void setFrames(Bitmap[] bmExplosionFrames) {
		if(bmExplosionFrames != null) {
			for(int i = 0; i < bmExplosionFrames.length; i++) {
				bitmaps[i] = bmExplosionFrames[i];
			}
		}
	}

	

}
