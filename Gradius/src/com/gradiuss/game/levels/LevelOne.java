package com.gradiuss.game.levels;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.gradiuss.game.GameView;
import com.gradiuss.game.GameViewActivity;
import com.gradiuss.game.R;
import com.gradiuss.game.audio.GameMusic;
import com.gradiuss.game.audio.OptionsActivity;
import com.gradiuss.game.audio.SoundEffects;
import com.gradiuss.game.models.AlienShip;
import com.gradiuss.game.models.Asteroid;
import com.gradiuss.game.models.Enemy;
import com.gradiuss.game.models.Explosion;
import com.gradiuss.game.models.ParallaxBackground;
import com.gradiuss.game.models.Projectile;
import com.gradiuss.game.models.SpaceShip;
import com.gradiuss.game.models.TypeOneProjectile;


public class LevelOne extends Level {
	private static final String TAG = LevelOne.class.getSimpleName();
	
	//Random generator
	private Random r;
	
	// Projectiles
	private List<Projectile> projectiles;
	private List<Projectile> projectileTypes;
	private Projectile proj1;
	private Projectile proj2;
	private int projectileTypePointer = 0;
	private float fireTime; // Measures how often a projectile will be fired
	private long previousFireTime = 0; // Measures the last time a projectile was fired
	
	// Enemies
	private List<Enemy> enemies;
	
	// Explosions
	private List<Explosion> explosions;
	private float explosionFrameTime; // Measures how long time an explosion-frame last.
	private List<Bitmap> bmExplosionFrames = new ArrayList<Bitmap>(9); // Array of Explosion Frames

	// Bitmaps
	private Bitmap bmAsteroid;
	private Bitmap bmAlienShip;
	
	// Background
	private ParallaxBackground parallaxBackground;
	
	// Audio
	SoundEffects soundEffects;
	
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// :::::::::::::::::::::::::::::::::::::::::::::: Initializing ::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	public LevelOne(Context context, int screenHeight, int screenWidth) {
		super(context, screenHeight, screenWidth);
	}
	
	@Override
	public void initializeLevel() {
		r = new Random();
		// Initialize Backgrounds
		initBackground();
		
		// Initialize SpaceShip
		initSpaceShip();
		
		// Initialize Projectiles
		initProjectiles();
		
		// Initialize Enemies
		initEnemies();
		
		// Initialize Explosions
		initExplosions();
		
		// Initialize Audio
		initAudio();
	}
	
	// TODO: TEMPORARY! LET EVERY PROJECTILE HAVE ITS OWN SoundEffect OBJEKT!
	private void initAudio() {
		// Sound Effects
		soundEffects = new SoundEffects(getContext());
		soundEffects.setShootSound(R.raw.shootsound, (float) 0.05);
		soundEffects.setExplosionSound(R.raw.explosion, (float) 1);
		float effects_volume = ((float) getContext().getSharedPreferences(OptionsActivity.filename, 0).getInt("effects_volume", 50))/100;
		SoundEffects.setVolume(effects_volume);
		
		// Music
		GameMusic.setContext(getContext());
		GameMusic.setMusic(R.raw.gamesong);
		float music_volume = ((float) getContext().getSharedPreferences(OptionsActivity.filename, 0).getInt("music_volume", 50))/100;
		GameMusic.setVolume(music_volume);
		GameMusic.playMusic();
	}

	/**
	 * Initializing ParallaxBackground.
	 */
	private void initBackground() {
		parallaxBackground = new ParallaxBackground(getScreenHeight(), getScreenWidth());
		Bitmap bmBackgroundBack = BitmapFactory.decodeResource(getResources(), R.drawable.spelbakgrundnypng);
		Bitmap bmBackgroundFront = BitmapFactory.decodeResource(getResources(), R.drawable.spelbakgrundnypng_front_big);
		parallaxBackground.addBackground(bmBackgroundBack, 1);
		parallaxBackground.addBackground(bmBackgroundFront, 2);
	}
	
	/**
	 * Initializing SpaceShip.
	 */
	private void initSpaceShip() {
		Bitmap bmSpaceShip = BitmapFactory.decodeResource(getResources(), R.drawable.spaceshipsnysmall2);
		Bitmap bmSpaceShipHit = BitmapFactory.decodeResource(getResources(), R.drawable.spaceshipsnysmall2_hit);
		
		// TODO - Common resolutions for smartphone screens vary from 240×320 to 720×1280, with many flagship Android phones at 
		// 480×800 or 540×960, the iPhone 4/4S at 640×960 and Galaxy Nexus and HTC Rezound at 720×1280 (Wikipedia). By these figures
		// the target standard for the spaceship size and other game-objects size should be calculated.
		Bitmap bmSpaceShipRightSize = Bitmap.createScaledBitmap(bmSpaceShip, (int)getScreenWidth()/8, (int)getScreenHeight()/6, true);
		Bitmap bmSpaceShipHitRightSize = Bitmap.createScaledBitmap(bmSpaceShipHit, (int)getScreenWidth()/8, (int)getScreenHeight()/6, true);
		
		List<Bitmap> spaceShipAnimations = new ArrayList<Bitmap>();
		spaceShipAnimations.add(bmSpaceShipRightSize);
		spaceShipAnimations.add(bmSpaceShipHitRightSize);
		
		setSpaceShip(new SpaceShip(spaceShipAnimations, getScreenWidth()/2, getScreenHeight()-bmSpaceShip.getHeight()));
		getSpaceShip().setVx(7);
		getSpaceShip().setVy(7);
		getSpaceShip().setVisible(true);
		getSpaceShip().setLife(100);
	}
	
	/**
	 * Initializing Projectiles.
	 */
	private void initProjectiles() {

		// List of projectile types
		projectileTypes = new ArrayList<Projectile>();
		projectileTypePointer = 0;
		fireTime = Projectile.FIRE_TIME_STANDARD;
		
		// Single Laser gun
		Bitmap bmTypeOneProjectile1 = BitmapFactory.decodeResource(getResources(), R.drawable.projectile1);
		proj1 = new TypeOneProjectile(bmTypeOneProjectile1, 0, 0);
		proj1.setVisible(true);
		proj1.setMoveUp(true);
		proj1.setVy(10);
		proj1.setDamage(15);
		proj1.setFireInterval((3 / 2) * Projectile.FIRE_TIME_STANDARD);
		projectileTypes.add(proj1);

		// Double Laser gun
		Bitmap bmTypeOneProjectile2 = BitmapFactory.decodeResource(getResources(), R.drawable.projectile2);
		proj2 = new TypeOneProjectile(bmTypeOneProjectile2, 0, 0);
		proj2.setVisible(true);
		proj2.setMoveUp(true);
		proj2.setVy(10);
		proj2.setDamage(20);
		proj2.setFireInterval((5 / 2) * Projectile.FIRE_TIME_STANDARD);
		projectileTypes.add(proj2);
		
		// Initializing ChangeWeapon-icon
		GameViewActivity.bChangeWeapon.setImageBitmap(projectileTypes.get(projectileTypePointer).getBitmap());
		
		// Projectiles ArrayList
		projectiles = new ArrayList<Projectile>();
	}
	
	/**
	 * Initializing Enemies.
	 */
	private void initEnemies() {
		bmAsteroid = BitmapFactory.decodeResource(getResources(), R.drawable.rocksmall);
		Bitmap bmAlienSpaceShip = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.attackers);
		bmAlienShip = Bitmap.createScaledBitmap(bmAlienSpaceShip, (int)GameView.width/8, (int)GameView.height/6, true);
		enemies = new ArrayList<Enemy>();
		
		// TODO - TEMPORARY: Initializing the first enemy to the list (MAKE THIS TIME-DEPENDANT!)
		Asteroid asteroid = new Asteroid(bmAsteroid, getScreenWidth()/2, 0);
		asteroid.setVy(r.nextInt(2) + 1);
		asteroid.setVx(r.nextInt(1));
		asteroid.setMoveDown(true);
		asteroid.setMoveRight(true);
		asteroid.setVisible(true);
		
		// add the first enemy to list of enemies
		enemies.add(asteroid);
	}
	
	/**
	 * Initializing Explosions.
	 */
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
	
	@Override
	public void updateLevel() {
		
		// Update Background
		updateBackground();
		
		// Update SpaceShip
		updateSpaceShip();
		
		// Update Projectiles
		updateProjectiles();
		
		// Update Enemiess
		updateEnemies();
		
		// Update Collisions
		updateCollisions();	
		
		// Update Explosions
		updateExplosions();		
	}
	
	/**
	 * Update SpaceShip.
	 */
	private void updateSpaceShip() {
		getSpaceShip().updateState();
	}
	
	@Override
	public void changeWeapon() {
		
		// Toggle between projectiles
		if (projectileTypePointer >= projectileTypes.size() - 1) {
			projectileTypePointer = 0;
		} else {
			projectileTypePointer++;
		}
		
		// Change the bitmap of the change-weapon-button
		GameViewActivity.bChangeWeapon.setImageBitmap(projectileTypes.get(projectileTypePointer).getBitmap());
	}
	
	/**
	 * Update projectiles.
	 */
	private void updateProjectiles() {

		// Shoot in certain time intervals
		if (getSpaceShip().isShooting() && getTotalGameTime() - previousFireTime > fireTime) {
			previousFireTime = getTotalGameTime();
			addProjectile(getSpaceShip().getX(), getSpaceShip().getY() - getSpaceShip().getBitmap().getHeight()/2);
			
			// TODO: TESTING SOUND EFFECTS
			soundEffects.playShootSound();
		}
		
		// Update state of all the projectiles in the list
		for (int i = projectiles.size() - 1; i >= 0; i--) {
			projectiles.get(i).updateState();
			
			// Remove if not on screen
			if (!projectiles.get(i).isVisible()) {
				projectiles.remove(i);
			}
		}
		
	}
	
	/**
	 * Add projectiles to the list.
	 * 
	 * @param x initial X-position of projectile.
	 * @param y initial Y-position of projectile.
	 */
	private void addProjectile(float x, float y) {
		Projectile projectile = new TypeOneProjectile(projectileTypes.get(projectileTypePointer));
		projectile.setX(x);
		projectile.setY(y);
		
		// Update the fireTime variable according to the type of projectiles
		fireTime = projectile.getFireInterval();
		projectiles.add(projectile);
	}
	
	/**
	 * Update enemies.
	 */
	private void updateEnemies() {
		for(Enemy e: enemies){
			e.updateState();
		}
	}
	
	/**
	 * Add asteroid-enemies.
	 */
	private void addAsteroid() {
		Asteroid asteroid = new Asteroid(bmAsteroid, r.nextInt(getScreenWidth()), -bmAsteroid.getHeight()/2);
		asteroid.setVy(r.nextInt(2)+2);
		asteroid.setVx(r.nextInt(3)-1);
		asteroid.setMoveDown(true);
		asteroid.setMoveRight(true);
		asteroid.setVisible(true);
		enemies.add(asteroid);
	}
	
	/**
	 * Add alien-enemies.
	 */
	private void addAlien() {
		AlienShip alien = new AlienShip(bmAlienShip, r.nextInt((int)GameView.width), -bmAlienShip.getHeight()/2, getSpaceShip());
		alien.setVy(2);
		alien.setVx(2);
		alien.setMoveDown(true);
		alien.setVisible(true);
//		alien.setLife(100);
//		alien.setDamage(80);
		enemies.add(alien);
		Log.d(TAG, "adding alien");
	}
	
	/**
	 * Add an Explosion in position x, y, with an explosion area.
	 * 
	 * @param x
	 * @param y
	 * @param explosionArea
	 */
	private void addExplosion(float x, float y, Rect explosionArea) { 
		Log.d(TAG, "explosionArea=" + explosionArea);
		Explosion explosion = new Explosion(bmExplosionFrames, x, y, explosionArea);
		explosion.setVisible(true);
		explosions.add(explosion);
		
		// TODO: TESTING EXPLOSION SOUND
		soundEffects.playExplosionSound();
		Log.d(TAG, "adding explosion");
	}
	
	/**
	 * Update Explosions
	 */
	private void updateExplosions() {
		for(Explosion explosion : explosions) {
			if(getTotalGameTime() - explosion.previousExplosionFrame > explosionFrameTime) {
				explosion.previousExplosionFrame = getTotalGameTime();
				explosion.updateState();
				if(explosion.lastFrame()) {
					explosions.remove(explosion);
				}
			}
		}
	}

	/**
	 *  Update Background.
	 */
	private void updateBackground() {
		parallaxBackground.updateState();
	}
	
	/**
	 *  Update Collisions between objects and boundaries
	 */
	private void updateCollisions() {
		collisionSpaceShipAndScreenEdges();
		collisionSpaceShipAndEnemies();
		collisionProjectilesAndEnemies();
		collisionEnemiesAndScreenEdges();
	}
	
	/**
	 * Update Collisions between SpaceShip and screen edges
	 */
	private void collisionSpaceShipAndScreenEdges() {
		
		// Collision: Spaceship and screen edges
		// Right Edge
		if (getSpaceShip().getX() >= getScreenWidth()) {
			getSpaceShip().setX(getScreenWidth());
		}
		// Left Edge
		if (getSpaceShip().getX() <= 0) {
			getSpaceShip().setX(0);
		}
		// Top Edge
		if (getSpaceShip().getY() <= 0) {
			getSpaceShip().setY(0);
		}
		// Bottom Edge
		if (getSpaceShip().getY() >= getScreenHeight()) {
			getSpaceShip().setY(getScreenHeight());
		} 		
	}

	/**
	 * Update Collisions between SpaceShip and Enemies
	 */
	private void collisionSpaceShipAndEnemies() {
		
		// Collision: Spaceship and Enemies
		// TODO - TEMPORARY SOLUTION: The spaceship should lose lifepower
		// and when it hits zero the game is over.
		for (Enemy enemy : enemies) {
			if (enemy.collisionDetection(getSpaceShip())) {
				
				// Remove lifepower from the spaceship
				getSpaceShip().setLife((int) (getSpaceShip().getLife() - enemy.getDamage()));
				getSpaceShip().setHit(true);
				
				// Save information about the enemy for the explosion
				enemy.setVisible(false);
				float x = enemy.getX();
				float y = enemy.getY();
				Rect rect = new Rect((int) x, (int) y, (int) x + enemy.getBitmapWidth(), (int) y + enemy.getBitmapHeight());
				enemies.remove(enemy);
				addExplosion(x, y, rect);
				if(r.nextBoolean()) {
					Log.d(TAG, "adding alien in updatingcollision");
					addAlien();
				} else {
					addAsteroid();
				}
				
				if (getSpaceShip().getLife() <= 0) {
					// TODO - TEMPORARY CODE: If spaceship has no life left make it invisible
					// TODO - SUGGESTION: Maybe we could handle "continues" so that a spacship has multiple lifes
					getSpaceShip().setVisible(false); 
					gameOver();
				}
			}
		}
	}
	
	/**
	 * Update Collisions between Projectiles and Enemies
	 */
	private void collisionProjectilesAndEnemies() {

		// Collision: Projectiles and Enemies
		for (Projectile projectile : projectiles) {	
			for (Enemy enemy : enemies) {
				
				if (projectile.collisionDetection(enemy)) {	
					// Destroy the projectile
					projectile.setVisible(false);
					
					// Logging how many projectiles there are in the list.
					Log.d(TAG, "projectiles.size() = " + projectiles.size());
					
					// Update the life of the enemy by the amount that the projectile inflicts.
					enemy.setLife(enemy.getLife() - projectile.getDamage());
					enemy.setHit(true);
					
					// Logging the life of the enemy
					Log.d(TAG, enemy.getLife() + "");
					
					// TODO - TEMPORARY SOLUTION: Destroy the enemy if lifebar <= 0 and add a new one to the top of the screen 
					// and add an explosion on previous position
					if (enemy.getLife() <= 0) {
						enemy.setVisible(false);
						float x = enemy.getX();
						float y = enemy.getY();
						Rect rect = new Rect((int) x, (int) y, (int) x + enemy.getBitmapWidth(), (int) y + enemy.getBitmapHeight());
						Log.d(TAG, "rect=" + rect.toShortString());
						enemies.remove(enemy);
						addExplosion(x, y, rect);
						Log.d(TAG, "enemies.size() = " + enemies.size());
						
						if(r.nextBoolean()) {
							Log.d(TAG, "adding alien in Projectiles and enemies collision");
							addAlien();
						} else {
							addAsteroid();
						}
					}
				} 
			}
		}
	}
	
	/**
	 * Update Collisions between Enemies and screen edges
	 */
	private void collisionEnemiesAndScreenEdges() {
		
		// Collision: Enemies and screen edges
		for (Enemy enemy : enemies) {
			if (enemy.getX() <= 0 - enemy.getBitmapWidth()/2 || enemy.getX() >= getScreenWidth() + enemy.getBitmapWidth()/2 || enemy.getY() <= 0 - enemy.getBitmapHeight()/2  || enemy.getY() >= getScreenHeight() + enemy.getBitmapHeight()/2) {
				enemy.setVisible(false);
				enemies.remove(enemy);
				
				// Logging how many enemies there are in the list.
				Log.d(TAG, "enemies.size() = " + enemies.size());
				
				if(r.nextBoolean()) {
					Log.d(TAG, "adding alien in updatingcollision");
					addAlien();
				} else {
					addAsteroid();
				}
			}
		}
	}
	
	// :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// :::::::::::::::::::::::::::::::::::::::::::::: Rendering ::::::::::::::::::::::::::::::::::::::::::::::
	// :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	@Override
	public void renderLevel(Canvas canvas) {
		renderBackground(canvas);
		renderSpaceShip(canvas);
		renderProjectiles(canvas);
		renderEnemies(canvas);
		renderExplosions(canvas);
	}

	/**
	 * Renders the background objects moving in parallel.
	 * @param canvas
	 */
	private void renderBackground(Canvas canvas) {
		parallaxBackground.draw(canvas);
	}

	/**
	 * Renders the Spaceship.
	 * @param canvas
	 */
	private void renderSpaceShip(Canvas canvas) {
		getSpaceShip().draw(canvas);
	}

	/**
	 * Renders the Projectiles.
	 * @param canvas
	 */
	private void renderProjectiles(Canvas canvas) {
		for (Projectile projectile : projectiles) {
			projectile.draw(canvas);
		}
	}

	/**
	 * Renders the enemies, rocks, aliens and more.
	 * @param canvas
	 */
	private void renderEnemies(Canvas canvas) {
		for(Enemy enemy : enemies){
			enemy.draw(canvas);
		}
	}

	/**
	 * Renders the explosion frames of different states of explosion
	 * @param canvas
	 */
	private void renderExplosions(Canvas canvas) {
		for(Explosion explosion : explosions) {
			explosion.draw(canvas);
		}
	}

}
