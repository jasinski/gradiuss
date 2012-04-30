package com.gradiuss.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageButton;

import com.gradiuss.game.models.Asteroid;
import com.gradiuss.game.models.Enemy;
import com.gradiuss.game.models.Explosion;
import com.gradiuss.game.models.ParallaxBackground;
import com.gradiuss.game.models.Projectile;
import com.gradiuss.game.models.SpaceShip;
import com.gradiuss.game.models.TypeOneProjectile;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// :::::::::::::::::::::::::::::::::::::::::::::: Fields ::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	private static final String TAG = GameView.class.getSimpleName();
	public GameLoopThread gameLoop;
	
	// GameView
	int width;
	int height;
	
	// Game time
	long startGameTime;
	long totalGameTime = 0;
	
	// Background
	ParallaxBackground parallaxBackground;
	
	// SpaceShip
	public SpaceShip spaceShip;
	
	// Projectiles
	public List<Projectile> projectiles;
	public List<Projectile> projectileTypes;
	Projectile proj1;
	Projectile proj2;
	Projectile proj3;
	Projectile proj4;
	int projectileTypePointer = 0;
	float fireTime; // Measures how often a projectile will be fired
	long previousFireTime = 0; // Measures the last time a projectile was fired
	
	
	// Enemies
	public List<Enemy> enemies;
	
	// Explosions
	public List<Explosion> explosions;
	float explosionFrameTime; // Measures how long time an explosion-frame last.
	
	// BITMAPS
	// Background
	Rect rectBackground;
	Bitmap bmSpaceShip;
	Bitmap bmTypeOneProjectile1;
	Bitmap bmTypeOneProjectile2;
	Bitmap bmAsteroid;
	
	// Array of Explosion Frames
	List<Bitmap> bmExplosionFrames = new ArrayList<Bitmap>(9);
		
	public GameView(Context context, AttributeSet attributes) {
		super(context, attributes);
		initGameView();
	}
	
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// :::::::::::::::::::::::::::::::::::::::::::::: Initializing ::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	// Loading resources like images, music etc... and starting the game loop!
	public void surfaceCreated(SurfaceHolder holder) {
		
		// GameView
		width = getWidth();
		height = getHeight();		
		
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
		Bitmap bmBackgroundBack = BitmapFactory.decodeResource(getResources(), R.drawable.spelbakgrundnypng);
		Bitmap bmBackgroundFront = BitmapFactory.decodeResource(getResources(), R.drawable.spelbakgrundnypng_front_big);
		
		Log.d("TESTING", "screen height(GameView) = " + height);
		
		parallaxBackground = new ParallaxBackground(height, width);
		parallaxBackground.addBackground(bmBackgroundBack, 1);
		parallaxBackground.addBackground(bmBackgroundFront, 2);
	}
 
	public void initGameObjects() {
		
		// Background
		initBackground();
		
		// SpaceShip
		initSpaceShip();
		
		// Projectiles
		initProjectiles();
		
		// Enemies
		initEnemies();
		initExplosions();
	}

	private void initSpaceShip() {
		bmSpaceShip = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.spaceshipsnysmall2);
		
		// TODO - Common resolutions for smartphone screens vary from 240×320 to 720×1280, with many flagship Android phones at 
		// 480×800 or 540×960, the iPhone 4/4S at 640×960 and Galaxy Nexus and HTC Rezound at 720×1280 (Wikipedia). By these figures
		// the target standard for the spaceship size and other game-objects size should be calculated.
		Bitmap bm = Bitmap.createScaledBitmap(bmSpaceShip, (int)width/8, (int)height/6, true);
		
		spaceShip = new SpaceShip(bm, width/2, height-bmSpaceShip.getHeight(), 5, 5);
		spaceShip.setVx(10);
		spaceShip.setVisible(true);
		
	}
	
	private void initProjectiles() {
		
		// Single Laser gun
		bmTypeOneProjectile1 = BitmapFactory.decodeResource(getResources(), R.drawable.projectile1);
		proj1 = new TypeOneProjectile(bmTypeOneProjectile1, 0, 0);
		proj1.setVisible(true);
		proj1.setMoveUp(true);
		proj1.setVy(10);
		proj1.setDamage(15);
		proj1.setFireInterval((3/2)*Projectile.FIRE_TIME_STANDARD);
		
		// Double Laser gun
		bmTypeOneProjectile2 = BitmapFactory.decodeResource(getResources(), R.drawable.projectile2);
		proj2 = new TypeOneProjectile(bmTypeOneProjectile2, 0, 0);
		proj2.setVisible(true);
		proj2.setMoveUp(true);
		proj2.setVy(10);
		proj2.setDamage(20);
		proj2.setFireInterval((5/2)*Projectile.FIRE_TIME_STANDARD);
		
		// List of projectile types
		projectileTypes = new ArrayList<Projectile>();
		projectileTypes.add(proj1);
		projectileTypes.add(proj2);
		projectileTypePointer = 0;
		GameViewActivity.bChangeWeapon.setImageBitmap(projectileTypes.get(projectileTypePointer).getBitmap());
		
		projectiles = new ArrayList<Projectile>();
		fireTime = Projectile.FIRE_TIME_STANDARD;
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
		bmExplosionFrames.add(BitmapFactory.decodeResource(getResources(), R.drawable.bmexplosion1));
		bmExplosionFrames.add(BitmapFactory.decodeResource(getResources(), R.drawable.bmexplosion2));
		bmExplosionFrames.add(BitmapFactory.decodeResource(getResources(), R.drawable.bmexplosion3));
		bmExplosionFrames.add(BitmapFactory.decodeResource(getResources(), R.drawable.bmexplosion4));
		bmExplosionFrames.add(BitmapFactory.decodeResource(getResources(), R.drawable.bmexplosion5));
		bmExplosionFrames.add(BitmapFactory.decodeResource(getResources(), R.drawable.bmexplosion6));
		bmExplosionFrames.add(BitmapFactory.decodeResource(getResources(), R.drawable.bmexplosion7));
		bmExplosionFrames.add(BitmapFactory.decodeResource(getResources(), R.drawable.bmexplosion8));
		bmExplosionFrames.add(BitmapFactory.decodeResource(getResources(), R.drawable.bmexplosion9));
	}

	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// :::::::::::::::::::::::::::::::::::::::::::::: Updating ::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	// Updating the states for all the game objects
	public void updateState() {
		updateBackground();
		
		// Update SpaceShip
		updateSpaceShip();
		updateProjectiles();
		
		updateEnemies();
		updateCollisions();	
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
		Projectile projectile = new TypeOneProjectile(projectileTypes.get(projectileTypePointer));
		projectile.setX(x);
		projectile.setY(y - spaceShip.getBitmap().getHeight()/2);
		
		fireTime = projectile.getFireInterval();
		projectiles.add(projectile);
		
	}
	
	public void changeWeapon() {
		if (projectileTypePointer >= projectileTypes.size() - 1) {
			projectileTypePointer = 0;
		} else {
			projectileTypePointer++;
		}
		GameViewActivity.bChangeWeapon.setImageBitmap(projectileTypes.get(projectileTypePointer).getBitmap());
	}
	
	// Update All Enemies
	private void updateEnemies() {
		for(Enemy e: enemies){
			e.updateState();
		}
	}
	
	// Create an Explosion in position x, y, with an explosion area
	private void addExplosion(float x, float y, Rect explosionArea) { 
		Explosion explosion = new Explosion(bmExplosionFrames, x, y, explosionArea);
		explosion.setVisible(true);
		explosions.add(explosion);
		Log.d(TAG, "adding explosion");
	}

	// Update Background
	private void updateBackground() {
		parallaxBackground.updateState();
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
//					projectiles.remove(projectile);
					
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
						
						Random r = new Random();
						Asteroid asteroid = new Asteroid(bmAsteroid, r.nextInt(width), -enemy.getBitmapHeight()/2);
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
			if (enemy.getX() <= 0 - enemy.getBitmapWidth()/2 || enemy.getX() >= getWidth() + enemy.getBitmapWidth()/2 || enemy.getY() <= 0 - enemy.getBitmapHeight()/2  || enemy.getY() >= getHeight() + enemy.getBitmapHeight()/2) {
				enemy.setVisible(false);
				enemies.remove(enemy);
				
				// Logging how many enemies there are in the list.
				Log.d(TAG, "enemies.size() = " + enemies.size());
				
				Random r = new Random();
				Asteroid asteroid = new Asteroid(bmAsteroid, r.nextInt(width), -enemy.getBitmapHeight()/2);
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

	// :::::::::::::::::::::::::::::::::::::::::::::: Rendering ::::::::::::::::::::::::::::::::::::::::::::::
	
	// Rendering the game state
	public void renderState(Canvas canvas) {
		renderBackground(canvas);
		renderSpaceShip(canvas);
		renderProjectiles(canvas);
		renderEnemies(canvas);
		renderExplosions(canvas);
	}
	
	public void renderBackground(Canvas canvas) {
		parallaxBackground.draw(canvas);
	}

	public void renderSpaceShip(Canvas canvas) {
		spaceShip.draw(canvas);
	}

	public void renderProjectiles(Canvas canvas) {
		for (Projectile projectile : projectiles) {
			projectile.draw(canvas);
		}
	}

	public void renderEnemies(Canvas canvas) {
		for(Enemy enemy : enemies){
			enemy.draw(canvas);
		}
	}

	public void renderExplosions(Canvas canvas) {
		for(Explosion explosion : explosions) {
			explosion.draw(canvas);
		}
	}

}