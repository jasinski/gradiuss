package com.gradiuss.game;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
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
import com.gradiuss.game.models.Explosion;
import com.gradiuss.game.models.Projectile;
import com.gradiuss.game.models.SpaceShip;
import com.gradiuss.game.models.TypeOneProjectile;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	
	// :::::::::::::::::::::::::::::::::::::::::::::: Fields ::::::::::::::::::::::::::::::::::::::::::::::
	
	private static final String TAG = GameView.class.getSimpleName();
	public GameLoopThread gameLoop;
	
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
	float fireTime; // Measures how often a projectile will be fired
	long previousFireTime = 0; // Measures the last time a projectile was fired
	
	// Enemies
	public List<Enemy> enemies;
	
	// Explosions
	public List<Explosion> explosions;
	float explosionFrameTime; //Measures how long time a explosion-frame last.
	
	// Background
	Bitmap bmBackground;
	Rect rectBackground;
	// Bitmaps
	Bitmap bmSpaceShip;
	Bitmap bmTypeOneProjectile1;
	Bitmap bmTypeOneProjectile2;
	Bitmap bmAsteroid;
	// Array of Explosion Frames
	Bitmap[] bmExplosionFrames = new Bitmap[9];
	
	
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
		
		// Starting time measurement
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
		initExplosions();
	}

	private void initSpaceShip() {
		// SpaceShip
		bmSpaceShip = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.spaceshipsnysmall);
		Bitmap bm = Bitmap.createScaledBitmap(bmSpaceShip, (int)width/8, (int)height/6, true);
		spaceShip = new SpaceShip(bm, width/2, height-bmSpaceShip.getHeight(), 5, 5);
		spaceShip.setVx(10);
		spaceShip.setVisible(true);
		
		// TODO - REMOVE: This line makes the spaceship shoot projectiles automatically until the firebutton is pressed.
//		spaceShip.setShooting(true);
	}
	
	private void initProjectiles() {
		// Projectiles
		bmTypeOneProjectile1 = BitmapFactory.decodeResource(getResources(), R.drawable.projectile1);
		bmTypeOneProjectile2 = BitmapFactory.decodeResource(getResources(), R.drawable.projectile2);
		projectiles = new ArrayList<Projectile>();
		fireTime = Projectile.FIRE_TIME_STANDARD;
//		projectileDamage = PROJECTILE_DAMAGE_ONE;
	}
	
	private void initEnemies() {
		bmAsteroid = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.rocksmall);
		enemies = new ArrayList<Enemy>();
		
		// TODO - TEMPORARY: Initializing the first enemy to the list (MAKE THIS TIME-DEPENDANT!)
		Asteroid asteroid = new Asteroid(bmAsteroid, width/2, 0);
		asteroid.setVy(new Random().nextInt(2) + 1);
		asteroid.setVx(new Random().nextInt(1));
		asteroid.setMoveDown(true);
		asteroid.setMoveRight(true);
		asteroid.setVisible(true);
		asteroid.setLife(100);
		
		// add enemies to list of enemies

		enemies.add(asteroid);
	}
	
	private void initExplosions() {
		explosions = new ArrayList<Explosion>(); 
		explosionFrameTime = Explosion.STANDARD_FRAME_TIME;
		bmExplosionFrames[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bmexplosion1);
		bmExplosionFrames[1] = BitmapFactory.decodeResource(getResources(), R.drawable.bmexplosion2);
		bmExplosionFrames[2] = BitmapFactory.decodeResource(getResources(), R.drawable.bmexplosion3);
		bmExplosionFrames[3] = BitmapFactory.decodeResource(getResources(), R.drawable.bmexplosion4);
		bmExplosionFrames[4] = BitmapFactory.decodeResource(getResources(), R.drawable.bmexplosion5);
		bmExplosionFrames[5] = BitmapFactory.decodeResource(getResources(), R.drawable.bmexplosion6);
		bmExplosionFrames[6] = BitmapFactory.decodeResource(getResources(), R.drawable.bmexplosion7);
		bmExplosionFrames[7] = BitmapFactory.decodeResource(getResources(), R.drawable.bmexplosion8);
		bmExplosionFrames[8] = BitmapFactory.decodeResource(getResources(), R.drawable.bmexplosion9);
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
		
		// Update Explosions
		updateExplosions();
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
			projectile.setDamage(10);
			projectile.setFireInterval((3/2)*Projectile.FIRE_TIME_STANDARD);
			projectiles.add(projectile);
			fireTime = projectile.getFireInterval();
			break;
		case 1:
			projectile = new TypeOneProjectile(bmTypeOneProjectile2, x, y - spaceShip.getBitmap().getHeight()/2);
			projectile.setVisible(true);
			projectile.setMoveUp(true);
			projectile.setVy(10);
			projectile.setDamage(20);
			projectile.setFireInterval((5/2)*Projectile.FIRE_TIME_STANDARD);
			projectiles.add(projectile);
			fireTime = projectile.getFireInterval();
			break;
		}
		
	}
	
	// Update All Enemies
	private void updateEnemies() {
		for(Enemy e: enemies){
			e.updateState();
		}
	}
	
	// Create an Explosion in position x, y, with an explosion area
	private void addExplosion(float x, float y, Rect explosionArea) { 
		Explosion explosion = new Explosion(x, y, explosionArea);
		explosion.setFrames(bmExplosionFrames);
		explosion.setVisible(true);
		//give the Explosion instance Resources so it can access the "png" files of them explosion frames
		explosions.add(explosion);
		Log.d(TAG, "adding explosion");
	}
	
	// Update collisions between objects and boundaries
	public void updateCollisions() {
		
		// Collision: Spaceship and screen edges
		// TODO - BUG: If the user keeps pressing the move button in the direction of the wall
		// then the spaceship will eventually move through it! FIX THIS!
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
		
		// Collision: Spaceship and Enemies
		// TODO - TEMPORARY SOLUTION: The spaceship should lose lifepower
		// and when it hits zero the game is over.
		for (Enemy enemy : enemies) {
			if (spaceShip.collisionDetection(enemy)) {
				spaceShip.setVisible(false);
				// TODO - method for explosion should be applied if enemy kills the spaceship.
				
			}
		}
		
		// Collision: Projectiles and Enemies
		for (Projectile projectile : projectiles) {	
			
			for (Enemy enemy : enemies) {
				
				if (projectile.collisionDetection(enemy)) {	
					// Destroy the projectile
					projectile.setVisible(false);
					
					// Logging how many projectiles there are in the list.
					Log.d(TAG, "projectiles.size() = " + projectiles.size());
					
					// Update the life of the enemy by the amount that the projectile inflicts.
					enemy.setLife((int) (enemy.getLife() - projectile.getDamage()));
					enemy.setHit(true);
					
					// Logging the life of the enemy
					Log.d(TAG, enemy.getLife() + "");
					
					// TODO - TEMPORARY SOLUTION: Destroy the enemy if lifebar <= 0 and add a new one to the top of the screen 
					// and add an explosion on previous position
					if (enemy.getLife() <= 0) {
						enemy.setVisible(false);
						float x = enemy.getX();
						float y = enemy.getY();
						Rect rect = enemy.getRect();
						enemies.remove(enemy);
						addExplosion(x, y, rect);
						Log.d(TAG, "enemies.size() = " + enemies.size());
						
						Asteroid asteroid = new Asteroid(bmAsteroid, width/2, -enemy.getHeight()/2);
						Random r = new Random();
						asteroid.setVy(r.nextInt(2)+2);
						asteroid.setVx(r.nextInt(3)-1);
						asteroid.setMoveDown(true);
						asteroid.setMoveRight(true);
						asteroid.setVisible(true);
						asteroid.setLife(100);
						enemies.add(asteroid);

					}
				} 
			}
		}
		

		// Collision: Enemies and screen edges
		for (Enemy enemy : enemies) {
			if (enemy.getX() <= 0 - enemy.getWidth()/2 || enemy.getX() >= getWidth() + enemy.getWidth()/2 || enemy.getY() <= 0 - enemy.getHeight()/2  || enemy.getY() >= getHeight() + enemy.getHeight()/2) {
				enemy.setVisible(false);
				enemies.remove(enemy);
				
				// Logging how many enemies there are in the list.
				Log.d(TAG, "enemies.size() = " + enemies.size());
				
				Asteroid asteroid = new Asteroid(bmAsteroid, width/2, -enemy.getHeight()/2);
				Random r = new Random();
				asteroid.setVy(r.nextInt(2)+2);
				asteroid.setVx(r.nextInt(3)-1);
				asteroid.setMoveDown(true);
				asteroid.setMoveRight(true);
				asteroid.setVisible(true);
				asteroid.setLife(100);
				enemies.add(asteroid);
			}
		}
	} //updateCollison()
	
	private void updateExplosions() {
		if(!explosions.isEmpty()) {
			for(Explosion explosion : explosions) {
				if(totalGameTime - explosion.previousExplosionFrame > explosionFrameTime) {
					explosion.previousExplosionFrame = totalGameTime;
					explosion.updateState();
					if(explosion.lastFrame()) {
						explosions.remove(explosion);
					}
				}			
			}
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
		renderExplosions(canvas);
	}
	

	// Render the Spaceship
	public void renderSpaceShip(Canvas canvas) {
		spaceShip.draw(canvas);
	}
	
	// Render All Projectiles
	public void renderProjectiles(Canvas canvas) {
		for (Projectile projectile : projectiles) {
			projectile.draw(canvas);
		}
	}
	
	// Render All Enemies
	public void renderEnemies(Canvas canvas) {
		for(Enemy enemy : enemies){
			enemy.draw(canvas);
		}
	}
	
	// Render All Explosions
	public void renderExplosions(Canvas canvas) {
		for(Explosion explosion : explosions) {
			explosion.draw(canvas);
		}
	}

}