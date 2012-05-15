package com.gradiuss.game.models;

import java.util.List;

import org.w3c.dom.Text;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

public class ScoreCounter extends GameObject {

	private int score;
	private List<Bitmap> bmNumbers;
	private float xMin, xMax, yMin, yMax;
	
	public ScoreCounter(List<Bitmap> animations, float x, float y, Rect rectangle) throws IllegalArgumentException {
		super(animations, x, y, rectangle);
		bmNumbers = animations;
		initialize();
	}
	
	public ScoreCounter(List<Bitmap> animations, float x, float y) throws IllegalArgumentException {
		super(animations, x, y);
		bmNumbers = animations;
		initialize();
	}
	
	@Override
	public void initialize() {
		score = 0;
	}
	
	public void addScore(int score) {
		this.score = this.score + score;
		
	}
	
	public void removeScore(int score) {
		this.score = this.score - score;
	}
	
	public void resetScore() {
		this.score = 0;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}

	@Override
	public void updateState() {
		
		//setBitmap(0, Bitmap.createScaledBitmap(getBitmap(), newWidth, (int) getBitmapHeight(), true));
	}

	@Override
	public void draw(Canvas canvas) {
		
		// TODO - TEMPORARY: paint the rectangle green, just for collision-testing 
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setStyle(Style.STROKE);
		canvas.drawText(score + "", getX(), getY(), paint);
//		super.draw(canvas);
	}
	
	

}
