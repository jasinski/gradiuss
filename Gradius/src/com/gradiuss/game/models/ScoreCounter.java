package com.gradiuss.game.models;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class ScoreCounter extends GameObject {

	private int score;
	private int[] numbersArray;
	float numberWidth, numberHeight;
	
	public ScoreCounter(List<Bitmap> animations, float x, float y) throws IllegalArgumentException {
		super(animations, x, y);
		numberHeight = animations.get(0).getHeight();
		numberWidth = animations.get(0).getWidth();
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
		//make score number to integer array
		int scoreTransitional = score;
		numbersArray = new int[10];
		for(int i = 0; i < numbersArray.length; i++) {
			numbersArray[i] = scoreTransitional % 10;
			scoreTransitional /= 10;
		}
	}
 	
	@Override
	public void draw(Canvas canvas) {
		for(int i = 0; i < numbersArray.length; i++) {
			//get number from array to draw at the right position.
			Bitmap bmScaledNum = Bitmap.createScaledBitmap(getAnimations().get(numbersArray[i]), (int)numberWidth/7, (int)numberHeight/8, true);
			//draw at right X-position
			canvas.drawBitmap(bmScaledNum, getX()-((numberWidth/7)*(i+1)), getY()+3, null);
		}
	}

}
