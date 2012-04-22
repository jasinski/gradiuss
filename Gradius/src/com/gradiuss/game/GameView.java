package com.gradiuss.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
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
	
	// Game Constants
	private static final String TAG = GameView.class.getSimpleName();
	private static final float FIRE_TIME_STANDARD = 100000000;
	private static final float FIRE_TIME_TYPE_ONE = (3/2) * FIRE_TIME_STANDARD;
	private static final float FIRE_TIME_TYPE_TWO = (5/2) * FIRE_TIME_STANDARD;
	private static final float PROJECTILE_DAMAGE_ONE = 10;
	private static final float PROJECTILE_DAMAGE_TWO = 20;
	public GameLoopThread gameLoop;
	
	// :::::::::::::::::::::::::::::::::::::::::::::: Fields ::::::::::::::::::::::::::::::::::::::::::::::
	
	// GameView
	int width;
	int height;
	
	// Game time
	long startGameTime;
	long totalGameTime = 0;
	
	// SpaceShip
	public SpaceShip spaceShip;
	
	// Projectiles
	public List<Projectile> projectiles;
	int projectileType = 1;
	float fireTime;
	long previousFireTime = 0;
	float projectileDamage;
	
	// Enemies
	public Asteroid asteroid;
	public List<Enemy> enemies;
	
	// Bitmaps
	Bitmap bmBackground;
	Rect rectBackground;
	Bitmap bmSpaceShip;
	Bitmap bmTypeOneProjectile1;
	Bitmap bmTypeOneProjectile2;
	Bitmap bmAsteroid;
	
	public GameView(Context context, AttributeSet attributes) {
		super(context, attributes);
		initGameView();
	}
	
	// :::::::::::::::::::::::::::::::::::::::::::::: Initializing ::::::::::::::::::::::::::::::::::::::::::::::
	
	// Loading resources like images, music etc... and starting the game loop!
	public void surfaceCreated(SurfaceHolder holder) {
		
		// GameView
		width = getWidth();
		height = getHeight();
		
		// Background
		initBackground();
		
		// Loading level (Resources)
		initGameObjects();
		
		// Starting game loop
		gameLoop.setRunning(true);
		gameLoop.start();
		startGameTime = System.nanoTime();
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
	
	public void initBackground() {
		bmBackground = BitmapFactory.decodeResource(getResources(), R.drawable.spelbakgrundnypng);
		rectBackground = new Rect(0, 0, width, height);
	}
 
	public void initGameObjects() {
		initSpaceShip();
		initProjectiles();
		initEnemies();
	}

	private void initSpaceShip() {
		// SpaceShip
		bmSpaceShip = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.spaceshipsmall);
		spaceShip = new SpaceShip(bmSpaceShip, width/2, height-bmSpaceShip.getHeight(), 5, 5);
		spaceShip.setVx(10);
		spaceShip.setVy(10);
		spaceShip.setVisible(true);
		spaceShip.setShooting(true);
	}
	
	private void initProjectiles() {
		// Projectiles
		bmTypeOneProjectile1 = BitmapFactory.decodeResource(getResources(), R.drawable.projectile1);
		bmTypeOneProjectile2 = BitmapFactory.decodeResource(getResources(), R.drawable.projectile2);
		projectiles = new ArrayList<Projectile>();
		fireTime = FIRE_TIME_TYPE_ONE;
		projectileDamage = PROJECTILE_DAMAGE_ONE;
	}
	
	private void initEnemies() {
		bmAsteroid = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.asteroid);
		enemies = new ArrayList<Enemy>();
		// Enemies
		asteroid = new Asteroid(bmAsteroid, width/2, 0);
		asteroid.setVy(1);
		asteroid.setVx(0);
		asteroid.setMoveDown(true);
		asteroid.setMoveRight(true);
		asteroid.setVisible(true);
		asteroid.setLife(100);
		enemies.add(asteroid);
		
	}
	
	// :::::::::::::::::::::::::::::::::::::::::::::: Updating ::::::::::::::::::::::::::::::::::::::::::::::
	
	// Updating the states for all the game objects
	public void updateState() {
		// Update SpaceShip
		updateSpaceShip();
		updateProjectiles();
		// Update Enemies
		updateEnemies();
		// Update collisions
		updateCollisions();	
	}

	public void updateProjectiles() {

		// Shooting projectiles
		if (spaceShip.isShooting() && totalGameTime - previousFireTime > fireTime) {
			previousFireTime = totalGameTime;
			addProjectile(spaceShip.getX(), spaceShip.getY());
		}
		
		for (int i = projectiles.size() - 1; i >= 0; i--) {
			projectiles.get(i).updateState();
			if (!projectiles.get(i).isVisible()) {
				projectiles.remove(i);
			}
		}
		
	}
	
	private void updateSpaceShip() {
		spaceShip.updateState();
	}
	
	
	public void addProjectile(float x, float y) {
		// Adding projectiles
		Projectile projectile;
		switch (projectileType) {
		case 0:
			projectile = new TypeOneProjectile(bmTypeOneProjectile1, x, y - spaceShip.getBitmap().getHeight()/2);
			projectile.setVisible(true);
			projectile.setMoveUp(true);
			projectile.setVy(10);
			projectiles.add(projectile);
			fireTime = FIRE_TIME_TYPE_ONE;
			projectileDamage = PROJECTILE_DAMAGE_ONE;
			break;
		case 1:
			projectile = new TypeOneProjectile(bmTypeOneProjectile2, x, y - spaceShip.getBitmap().getHeight()/2);
			projectile.setVisible(true);
			projectile.setMoveUp(true);
			projectile.setVy(10);
			projectiles.add(projectile);
			fireTime = FIRE_TIME_TYPE_TWO;
			projectileDamage = PROJECTILE_DAMAGE_TWO;
			break;
		}
		
	}
	
	// Update All Enemies
	private void updateEnemies() {
		updateAsteroid();
	}
	
	// Update individual Enemies
	private void updateAsteroid() {
		asteroid.updateState();
	}
	
	// Update collisions between objects and boundaries
	public void updateCollisions() {
		
		// Spaceship and screenedges
		if (spaceShip.getX() + spaceShip.getBitmap().getWidth()/2 >= width) {
			spaceShip.setMoveRight(false);
		}
		if (spaceShip.getX() - spaceShip.getBitmap().getWidth()/2 <= 0) {
			spaceShip.setMoveLeft(false);
		}
		if (spaceShip.getY() - spaceShip.getBitmap().getHeight()/2 <= 0) {
			spaceShip.setMoveUp(false);
		}
		if (spaceShip.getY() + spaceShip.getBitmap().getHeight()/2 >= height) {
			spaceShip.setMoveDown(false);
		}
		
		// Spaceship and enemies
		if (Rect.intersects(spaceShip.getRectangle(), asteroid.getRectangle())) {
			//spaceShip.setVisible(false);
		}
		
		// Projectiles and enemies
		for (int i = projectiles.size()-1; i > -1; i--) {
			if (projectiles.get(i).getRectangle().intersect(asteroid.getRectangle())) {
				projectiles.get(i).setVisible(false);
				asteroid.setLife((int) (asteroid.getLife()-projectileDamage));
				float shrinkPercentage = (100/projectileDamage-1)/(100/projectileDamage);
				Log.d(TAG, shrinkPercentage + "");
				asteroid.setBitmap(Bitmap.createBitmap(bmAsteroid, 0, 0, Math.round(shrinkPercentage*asteroid.getBitmap().getWidth()), Math.round(shrinkPercentage*asteroid.getBitmap().getHeight())));
				
				Log.d(TAG, asteroid.getLife() + "");
				
				if (asteroid.getLife() <= 0) {
					asteroid = new Asteroid(bmAsteroid, width/2, -asteroid.getHeight()/2);
					Random r = new Random();
					asteroid.setVy(r.nextInt(2)+2);
					asteroid.setVx(r.nextInt(3)-1);
					asteroid.setMoveDown(true);
					asteroid.setMoveRight(true);
					asteroid.setVisible(true);
					asteroid.setLife(100);
					//enemies.add(asteroid);
				} 
			}
		}
		
		if (asteroid.getX() <= 0 - asteroid.getWidth()/2 || asteroid.getX() >= getWidth() + asteroid.getWidth()/2 || asteroid.getY() <= 0 - asteroid.getHeight()/2  || asteroid.getY() >= getHeight() + asteroid.getHeight()/2) {
			asteroid = new Asteroid(bmAsteroid, width/2, -asteroid.getHeight()/2);
			Random r = new Random();
			asteroid.setVy(r.nextInt(2)+2);
			asteroid.setVx(r.nextInt(3)-1);
			asteroid.setMoveDown(true);
			asteroid.setMoveRight(true);
			asteroid.setVisible(true);
			asteroid.setLife(100);
			//enemies.add(asteroid);
		}
		
	}
	
	
	// :::::::::::::::::::::::::::::::::::::::::::::: Rendering ::::::::::::::::::::::::::::::::::::::::::::::
	
	// Rendering the game state
	public void renderState(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		canvas.drawBitmap(bmBackground, null, rectBackground, null);
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
		renderAsteroid(canvas);
	}
	
	private void renderAsteroid(Canvas canvas) {
		asteroid.draw(canvas);
	}

}