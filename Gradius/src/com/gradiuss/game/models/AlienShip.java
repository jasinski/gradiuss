package com.gradiuss.game.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class AlienShip extends Enemy {
	private static final String TAG = AlienShip.class.getSimpleName();
	private boolean hit;
	private float previousLife;
	private static final int INITIAL_LIFE = 100;
	private static final int INITIAL_DAMAGE = 80;
	private SpaceShip target;

	public AlienShip(Bitmap bitmap, float x, float y, SpaceShip target) {
		super(bitmap, x, y);
		setLife(INITIAL_LIFE);
		setDamage(INITIAL_DAMAGE);
		this.target = target;
		initialize();
	}

	@Override
	public void initialize() {
		SCORE = 50;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public boolean isHit() {
		return hit;
	}

	@Override
	public void setLife(float life) {
		previousLife = getLife();
		super.setLife(life);
	}

	@Override
	public void updateState() {

		// Stop shooting
		setShooting(false);

		// Calculate the difference in X and Y between the alien ship and the
		// spaceship (target).
		float xDiff = getX() - target.getX();
		float yDiff = getY() - target.getY();

		// If alienship is below the spaceship stop shooting.
		if (yDiff < 0) {

			// Positive xDiff => coming from right
			if (xDiff >= (0.3 * (float) target.getBitmapWidth())) {
				setMoveRight(false);
				setMoveLeft(true);

				// Start shooting if difference in X is smaller than or equal to 0
				if (xDiff <= 0) {
					setMoveLeft(false);
					setShooting(true);
					float x = (float) (0.3 * (float) target.getBitmapWidth());
				}
				// Negative xDiff => coming from left
			} else if (xDiff <= (0.3 * (float) target.getBitmapWidth())) {
				setMoveLeft(false);
				setMoveRight(true);
				
				// Start shooting if difference in X is larger than or equal to 0
				if (xDiff >= 0) {
					setMoveRight(false);
					setShooting(true);
					float x = (float) (0.3 * (float) target.getBitmapWidth());
				}
			} else { // Otherwise
				setMoveRight(false);
				setMoveLeft(false);
			}
		}

		// Movement
		if (moveLeft) { // Left
			setX((getX() + (getVx() * MovingObject.DIRECTION_LEFT)));
		}
		if (moveRight) { // Right
			setX((getX() + (getVx() * MovingObject.DIRECTION_RIGHT)));
		}
		if (moveUp) { // Up
			setY((getY() + (getVy() * MovingObject.DIRECTION_UP)));
		}
		if (moveDown) { // Down
			setY((getY() + (getVy() * MovingObject.DIRECTION_DOWN)));
		}
		
		// Hit by projectile
		if (isHit()) {
			float damage = previousLife - getLife();
			setDamage((int) (damage));
			setHit(false);
		}
		super.updateState();
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}

	@Override
	public String toString() {
		return "ALIEN";
	}

}
