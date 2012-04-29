package com.gradiuss.game.models;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Explosion extends GameObject {

	Bitmap[] bitmaps = new Bitmap[9];
	
	private int bitmapNr = 0;
	public static final float STANDARD_FRAME_TIME = 50000000; //Measure for how long time frame should be shown
	public long previousExplosionFrame = 0; //Measures the last time the picture of a explosion-state changed
	
//	public Explosion(float x, float y, Rect rect) {
//		super(null, x, y, rect);
//	}
	
	public Explosion(List<Bitmap> animations, float x, float y, Rect rectangle) throws IllegalArgumentException {
		super(animations, x, y, rectangle);
	}
	
	public void updateState() {
		
		// GameObject har ej int-pekare som kan incrementeras och som löper igenom listan med animationer (se superklassen)
		nextAnimation();
		
//		setBitmap(0, bitmaps[bitmapNr]);
//		if(bitmapNr < 8) {
//			bitmapNr++;
//		}
		super.updateState();
	}
	
	public boolean lastFrame() {
		return getAnimationPointer() == getAnimations().size()-1;
	}
	
	// Den här metoden behövs ej längre eftersom alla objekt har nu en lista med animationer i superklassen GameObject
	
	//set the explosion frames with resources from GameView
//	public void setFrames(Bitmap[] bmExplosionFrames) {
//		if(bmExplosionFrames != null) {
//			for(int i = 0; i < bmExplosionFrames.length; i++) {
//				bitmaps[i] = bmExplosionFrames[i];
//			}
//		}
//	}

	

}
