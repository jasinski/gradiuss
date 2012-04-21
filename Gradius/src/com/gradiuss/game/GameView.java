package com.gradiuss.game;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gradiuss.game.models.Asteroid;
import com.gradiuss.game.models.Enemy;
import com.gradiuss.game.models.Projectile;
import com.gradiuss.game.models.SpaceShip;
import com.gradiuss.game.models.TypeOneProjectile;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	
	private static final String TAG = GameView.class.getSimpleName();
	public GameLoopThread gameLoop;
	
	// GameObjects
	public SpaceShip spaceShip;
	public List<Projectile> projectiles;
	int projectileType = 0;
	public List<Enemy> enemies;
	public Asteroid asteroid;
	
	public GameView(Context context, AttributeSet attributes) {
		super(context, attributes);
		initGameView();
	}
	
	// :::::::::::::::::::::::::::::::::::::::::::::: Initializing ::::::::::::::::::::::::::::::::::::::::::::::
	
	// Loading resources like images, music etc... and starting the game loop!
	public void surfaceCreated(SurfaceHolder holder) {
		
		// Loading level (Resources)
		initGameObjects();
		
		// Starting game loop
		gameLoop.setRunning(true);
		gameLoop.start();
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO: Fixa till senare!
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				gameLoop.join();
				retry = false;
			} catch (InterruptedException e) {
				// Try again, shutting down the thread(game loop)
			}
		}
		
	}
	
	private void initGameView() {
		getHolder().addCallback(this);
		gameLoop = new GameLoopThread(getHolder(), this);
		setFocusable(true);
	}

	
	public void initGameObjects() {
		initSpaceShip();
		initProjectiles();
		initEnemies();
	}

	private void initSpaceShip() {
		// SpaceShip
		Bitmap spaceShipBitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.spaceship);
		spaceShip = new SpaceShip(spaceShipBitmap, getWidth()/2, getHeight()-spaceShipBitmap.getHeight(), 5, 5);
		spaceShip.setVx(10);
		spaceShip.setVisible(true);
	}
	
	private void initProjectiles() {
		// Projectiles
		projectiles = new ArrayList<Projectile>();
	}
	
	private void initEnemies() {
		enemies = new ArrayList<Enemy>();
		// Individual Enemies
		Bitmap asteroidBitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.asteroid);
		asteroid = new Asteroid(asteroidBitmap, 0, 0);
		// add enemies to list of enemies
		enemies.add(asteroid);
	}
	
	// :::::::::::::::::::::::::::::::::::::::::::::: Updating ::::::::::::::::::::::::::::::::::::::::::::::
	
	// Updating the states for all the game objects
	public void updateState() {
		//check for all collisions
		for(Enemy e : enemies) {
			if (spaceShip.collisionDetection(e)) {
				spaceShip.setVisible(false);
			}
		}
		// Update SpaceShip
		updateSpaceShip();
		updateProjectiles();
		// Update Enemies
		updateEnemies();
	}

	public void updateProjectiles() {
		// Shoots projectile
		if (spaceShip.isShooting()) {
			addProjectile(spaceShip.getX(), spaceShip.getY());
		}
		
		for (int i = projectiles.size() - 1; i >= 0; i--) {
			projectiles.get(i).updateState();
			if (projectiles.get(i).isVisible() == false) {
				projectiles.remove(i);
			}
		}
		// Shoots projectile
	}
	
	private void updateSpaceShip() {
		// Collisions with the Edges
		if (spaceShip.getX() + spaceShip.getBitmap().getWidth()/2 >= getWidth()) {
			spaceShip.setMoveRight(false);
		}
		if ((spaceShip.getX() - spaceShip.getBitmap().getWidth()/2 <= 0)) {
			spaceShip.setMoveLeft(false);
		}
		spaceShip.updateState();
		
	}
	
	
	public void addProjectile(float x, float y) {
		// Adding projectiles
		Projectile projectile;
		switch (projectileType) {
		case 0:
			projectile = new TypeOneProjectile(BitmapFactory.decodeResource(getResources(), R.drawable.projectile1), x, y - spaceShip.getBitmap().getHeight()/2);
			projectile.setVisible(true);
			projectile.setMoveUp(true);
			projectile.setVy(50);
			projectiles.add(projectile);
			break;
		case 1:
			projectile = new TypeOneProjectile(BitmapFactory.decodeResource(getResources(), R.drawable.projectile2), x, y - spaceShip.getBitmap().getHeight()/2);
			projectile.setVisible(true);
			projectile.setMoveUp(true);
			projectile.setVy(50);
			projectiles.add(projectile);
			break;
		}
		
	}
	
	// Update All Enemies
	private void updateEnemies() {
		for(Enemy e: enemies){
			e.updateState();
		}
	}
	
	// :::::::::::::::::::::::::::::::::::::::::::::: Rendering ::::::::::::::::::::::::::::::::::::::::::::::
	
	// Rendering the game state
	public void renderState(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		renderSpaceShip(canvas);
		renderProjectiles(canvas);
		renderEnemies(canvas);
	}

	public void renderSpaceShip(Canvas canvas) {
		spaceShip.draw(canvas);
	}
	
	public void renderProjectiles(Canvas canvas) {
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).draw(canvas);
		}
	}
	
	// Render All Enemies
	private void renderEnemies(Canvas canvas) {
		for(Enemy e : enemies){
			e.draw(canvas);
		}
	}

	
}