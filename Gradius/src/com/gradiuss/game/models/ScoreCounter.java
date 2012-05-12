package com.gradiuss.game.models;

import org.w3c.dom.Text;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class ScoreCounter extends GameObject {

	private int score;
	
	public ScoreCounter() throws IllegalArgumentException {
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
