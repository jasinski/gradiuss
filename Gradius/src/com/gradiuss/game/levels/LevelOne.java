package com.gradiuss.game.levels;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.gradiuss.game.GameLoopThread;
import com.gradiuss.game.GameView;
import com.gradiuss.game.GameViewActivity;
import com.gradiuss.game.R;
import com.gradiuss.game.audio.GameMusic;
import com.gradiuss.game.audio.OptionsActivity;
import com.gradiuss.game.audio.SoundEffects;
import com.gradiuss.game.models.AlienProjectile;
import com.gradiuss.game.models.AlienShip;
import com.gradiuss.game.models.Asteroid;
import com.gradiuss.game.models.Enemy;
import com.gradiuss.game.models.Explosion;
import com.gradiuss.game.models.LifeBar;
import com.gradiuss.game.models.ParallaxBackground;
import com.gradiuss.game.models.Projectile;
import com.gradiuss.game.models.ScoreCounter;
import com.gradiuss.game.models.SpaceShip;
import com.gradiuss.game.models.TypeOneProjectile;


public class LevelOne extends Level {
	private static final String TAG = LevelOne.class.getSimpleName();
	
	//Random generator
	private Random random;
	
	// Projectiles
	private List<Projectile> projectiles;
	private List<Projectile> projectileTypes;
	private Projectile proj1;
	private Projectile proj2;
	private int projectileTypePointer = 0;
	private float fireTime; // Measures how often a projectile will be fired
	private long previousFireTime = 0; // Measures the last time a projectile was fired
	
	// Enemy projectiles
	private List<Projectile> enemyProjectiles;
	private List<Projectile> enemyProjectileTypes;
	private Projectile enemyProj1;
	private int enemyProjectileTypePointer = 0;
	
	// Enemies
	private List<Enemy> enemies;
	private float previousEnemyAddedTime = 0; // Measures the last time an enemy was fired
	private float enemyTimeInterval  = 9000000000l;
	private float previousEnemyFrequencyUpdate = 0;
	private float enemyFrequencyTime = 30000000000l; // Update the enemy update frequency every 30 sec
	
	// Explosions
	private List<Explosion> explosions;
	private float explosionFrameTime; // Measures how long time an explosion-frame last.
	private List<Bitmap> bmExplosionFrames = new ArrayList<Bitmap>(9); // Array of Explosion Frames

	// Bitmaps
	private Bitmap bmAsteroid;
	private Bitmap bmAlienShip;
	
	// Background
	private ParallaxBackground parallaxBackground;
	
	// Status bar objects
	private LifeBar lifeBar;
	private ScoreCounter scoreCounter;
	private Bundle scoreBundle;
	
	// Audio
	private SoundEffects soundEffects;

	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// :::::::::::::::::::::::::::::::::::::::::::::: Initializing ::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	public LevelOne(Context context, GameLoopThread gameLoop, int screenHeight, int screenWidth) {
		super(context, gameLoop, screenHeight, screenWidth);
	}
	
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// :::::::::::::::::::::::::::::::::::::::::::::: Initializing ::::::::::::::::::::::::::::::::::::::::::::::
	// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	
	
	@Override
	public void initializeLevel() {
		
		// Initialize variables
		initVariables();
		
		// Initialize Backgrounds
		initBackground();
		
		// Initialize variables
		initStatusBar();
		
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
	
	private void initStatusBar() {
		// Life bar
		List<Bitmap> lifeBarBitmaps = new ArrayList<Bitmap>(2);
		Bitmap bmlifeBar = BitmapFactory.decodeResource(getResources(), R.drawable.life_bar);
		Bitmap bmlifeBarFrame = BitmapFactory.decodeResource(getResources(), R.drawable.life_bar_frame);
		lifeBarBitmaps.add(bmlifeBar);
		lifeBarBitmaps.add(bmlifeBarFrame);
		lifeBar = new LifeBar(lifeBarBitmaps, 0, 0);
		lifeBar.setVisible(true);
		
		// Score counter
		scoreCounter = new ScoreCounter();
		scoreCounter.setX(getScreenWidth() - 50);
		scoreCounter.setY(0 + 50);
		scoreCounter.setVisible(true);
	}

	private void initVariables() {
		random = new Random();
		scoreBundle = new Bundle();
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
		
		Bitmap bmSpaceShipRightSize = Bitmap.createScaledBitmap(bmSpaceShip, (int)getScreenWidth()/8, (int)getScreenHeight()/6, true);
		Bitmap bmSpaceShipHitRightSize = Bitmap.createScaledBitmap(bmSpaceShipHit, (int)getScreenWidth()/8, (int)getScreenHeight()/6, true);
		
		List<Bitmap> spaceShipAnimations = new ArrayList<Bitmap>();
		spaceShipAnimations.add(bmSpaceShipRightSize);
		spaceShipAnimations.add(bmSpaceShipHitRightSize);
		
		setSpaceShip(new SpaceShip(spaceShipAnimations, getScreenWidth()/2, getScreenHeight()-bmSpaceShip.getHeight()));
		getSpaceShip().setVx(7);
		getSpaceShip().setVy(7);
		getSpaceShip().setVisible(true);
//		getSpaceShip().setLife(lifeBar.getLifeBar());
	}
	
	/**
	 * Initializing Projectiles.
	 */
	private void initProjectiles() {

		// List of projectile types
		projectileTypes = new ArrayList<Projectile>();
		enemyProjectileTypes = new ArrayList<Projectile>();
		projectileTypePointer = 0;
		enemyProjectileTypePointer = 0;
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
		
		// Aliens projectile type
		Bitmap bmAlienProjectile = BitmapFactory.decodeResource(getResources(), R.drawable.projectile_alien);
		enemyProj1 = new AlienProjectile(bmAlienProjectile, 0, 0);
		enemyProj1.setVisible(true);
		enemyProj1.setMoveDown(true);
		enemyProj1.setVy(10);
		enemyProj1.setDamage(20);
		enemyProjectileTypes.add(enemyProj1);
		
		// Initializing ChangeWeapon-icon
		GameViewActivity.bChangeWeapon.setImageBitmap(projectileTypes.get(projectileTypePointer).getBitmap());
		
		// Projectiles ArrayList
		projectiles = new ArrayList<Projectile>();
		enemyProjectiles = new ArrayList<Projectile>();
	}
	
	/**
	 * Initializing Enemies.
	 */
	private void initEnemies() {
		bmAsteroid = BitmapFactory.decodeResource(getResources(), R.drawable.rocksmall);
		Bitmap bmAlienSpaceShip = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.attackers);
		bmAlienShip = Bitmap.createScaledBitmap(bmAlienSpaceShip, (int)GameView.width/8, (int)GameView.height/6, true);
		enemies = new ArrayList<Enemy>();
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
		
		// Update Status bar
		updateStatusBar();
		
	}
	
	private void updateStatusBar() {
		// Life bar
		updateLifeBar();
		lifeBar.updateState();
		// Score counter
		scoreCounter.updateState();
	}

	/**
	 * Update SpaceShip.
	 */
	private void updateSpaceShip() {
		getSpaceShip().updateState();
	}
	
	
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
		
		for(int i = 0; i < enemies.size(); i++) {
//		for(Enemy enemy : enemies) {
			if(enemies.get(i).isVisible() && enemies.get(i).isShooting() && getTotalGameTime() - enemies.get(i).previousEnemyFireTime > enemies.get(i).enemyFireTime) {
				enemies.get(i).previousEnemyFireTime = getTotalGameTime();
				addEnemyProjectile(enemies.get(i).getX(), enemies.get(i).getY() + enemies.get(i).getBitmapHeight()/2, enemies.get(i));
				
				// TODO: TESTING SOUND EFFECTS
				soundEffects.playShootSound();
			}
		}
		
		// Update state of all the projectiles in the list
		for (int i = projectiles.size() - 1; i >= 0; i--) {
			projectiles.get(i).updateState();
			
			// Remove if not on screen
			if (!projectiles.get(i).isVisible()) {
				projectiles.remove(i);
			}
		}
		
		// Update state of all the enemy projectiles in the list
		for (int i = enemyProjectiles.size() - 1; i >= 0; i--) {
			enemyProjectiles.get(i).updateState();
					
			// Remove if not on screen
			if (!enemyProjectiles.get(i).isVisible()) {
				enemyProjectiles.remove(i);
			}
		}	
	}
	
	/**
	 * Add projectiles to the projectiles list.
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
	 * Add Enemy projectiles to the enemyprojectiles list.
	 * 
	 * @param x initial X-position of projectile.
	 * @param y initial Y-position of projectile.
	 * @param alien Enemy object with shooting capacity to get its shooting fireinterval time to set the projectiles firetime.
	 */
	private void addEnemyProjectile(float x, float y, Enemy alien) {
		Projectile enemyProjectile = new AlienProjectile(enemyProjectileTypes.get(enemyProjectileTypePointer));
		enemyProjectile.setX(x);
		enemyProjectile.setY(y);
		//Setting window height so projectile knows when it is off screen
		enemyProjectile.setWindowHeight(getScreenHeight());
		Log.d(TAG, "adding enemyprojectile");
		//Setting the specific projectiles firetime according to enemys firetime.
		enemyProjectile.setFireInterval(alien.enemyFireTime);
		enemyProjectiles.add(enemyProjectile);
		Log.d(TAG, "size of enemyprojectiles = " + Integer.toString(enemyProjectiles.size()));
	}
	
	/**
	 * Add Enemy to the enemies list.
	 * 
	 * @param x initial X-position of enemy.
	 * @param y initial Y-position of enemy.
	 * @param alien Enemy object with shooting capacity to get its shooting fireinterval time to set the projectiles firetime.
	 */
	private void addEnemy() {
		boolean test = random.nextBoolean();
		Log.d(TAG, "random.nextBoolean()=" + test); 
		if(test) {
			Log.d(TAG, "Adding alien!");
			addAlien();
		} else {
			Log.d(TAG, "Adding asteroid!");
			addAsteroid();
		}
	}
	
	/**
	 * Update enemies.
	 */
	private void updateEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).updateState();
		}
		
		// TODO: HÄR ÄR JAG!!!
		// TODO: HÄR ÄR JAG!!!
		// TODO: HÄR ÄR JAG!!!
		// TODO: HÄR ÄR JAG!!!
		// TODO: HÄR ÄR JAG!!!
		if (getTotalGameTime() - previousEnemyFrequencyUpdate > enemyFrequencyTime) {
			previousEnemyFrequencyUpdate = getTotalGameTime();
			enemyTimeInterval = enemyTimeInterval-enemyTimeInterval/10;
			Log.d("TESTAR", "enemyTimeInterval=" + enemyTimeInterval);
		}	
		// TODO: HÄR ÄR JAG!!!
		// TODO: HÄR ÄR JAG!!!
		// TODO: HÄR ÄR JAG!!!
		// TODO: HÄR ÄR JAG!!!
		// TODO: HÄR ÄR JAG!!!
		
		
		if (getTotalGameTime() - previousEnemyAddedTime > enemyTimeInterval) {
			previousEnemyAddedTime = getTotalGameTime();
//			Log.d(TAG, "Borde adda enemy!");
			Log.d("TESTAR", "adding enemy because of time update");
			addEnemy();
		}
		

		
	}
	
	/**
	 * Add asteroid-enemies.
	 */
	private void addAsteroid() {
		Asteroid asteroid = new Asteroid(bmAsteroid, random.nextInt(getScreenWidth()), -bmAsteroid.getHeight()/2);
		asteroid.setVy(random.nextInt(2)+2);
		asteroid.setVx(random.nextInt(3)-1);
		asteroid.setMoveDown(true);
		asteroid.setMoveRight(true);
		asteroid.setVisible(true);
		enemies.add(asteroid);
	}
	
	/**
	 * Add alien-enemies.
	 */
	private void addAlien() {
		AlienShip alien = new AlienShip(bmAlienShip, random.nextInt((int)GameView.width), -bmAlienShip.getHeight()/2, getSpaceShip());
		alien.setVy(2);
		alien.setVx(2);
		alien.setMoveDown(true);
		alien.setVisible(true);
		alien.enemyFireTime = (8 / 2) * Projectile.FIRE_TIME_STANDARD;
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
		for(int i = 0; i < explosions.size(); i++) {
			if(getTotalGameTime() - explosions.get(i).previousExplosionFrame > explosionFrameTime) {
				explosions.get(i).previousExplosionFrame = getTotalGameTime();
				explosions.get(i).updateState();
				if(explosions.get(i).lastFrame()) {
					explosions.remove(explosions.get(i));
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
		collisionProjectilesAndSpaceShip();
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
		for(int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).collisionDetection(getSpaceShip())) {

				// Update LifeBar
				lifeBar.setHit(true);
				lifeBar.decreaseLife(enemies.get(i).getDamage());
//				lifeBar.setLifeBar(lifeBar.getLifeBar() - enemies.get(i).getDamage());
				Log.d(TAG, "YOUR LIFE IS: " + lifeBar.getLifeBar());
				
				// TODO - TEMPORARY: VIBRATE
				Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
				long milliseconds = 100;
				v.vibrate(milliseconds);
				// VIBRATE

				// Save information about the enemy for the explosion
				enemies.get(i).setVisible(false);
				float x = enemies.get(i).getX();
				float y = enemies.get(i).getY();
				Rect rect = new Rect((int) x, (int) y, (int) x + enemies.get(i).getBitmapWidth(), (int) y + enemies.get(i).getBitmapHeight());
				enemies.remove(enemies.get(i));
				addExplosion(x, y, rect);
				
			}
			
		}
		
	}
	
	/**
	 * Update Collisions between Projectiles and Enemies
	 */
	private void collisionProjectilesAndEnemies() {

		// Collision: Projectiles and Enemies
		for (Projectile projectile : projectiles) {	
			for (int i = 0; i < enemies.size(); i++) {
//			for (Enemy enemy : enemies) {
				
				if (projectile.collisionDetection(enemies.get(i))) {	
					// Destroy the projectile
					projectile.setVisible(false);
					
					// Logging how many projectiles there are in the list.
					Log.d(TAG, "projectiles.size() = " + projectiles.size());
					
					// Update the life of the enemy by the amount that the projectile inflicts.
					enemies.get(i).setLife(enemies.get(i).getLife() - projectile.getDamage());
					enemies.get(i).setHit(true);
					
					// Logging the life of the enemy
					Log.d(TAG, enemies.get(i).getLife() + "");
					
					// TODO - TEMPORARY SOLUTION: Destroy the enemy if lifebar <= 0 and add a new one to the top of the screen 
					// and add an explosion on previous position
					if (enemies.get(i).getLife() <= 0) {
						scoreCounter.addScore(enemies.get(i).SCORE);
						enemies.get(i).setVisible(false);
						float x = enemies.get(i).getX();
						float y = enemies.get(i).getY();
						Rect rect = new Rect((int) x, (int) y, (int) x + enemies.get(i).getBitmapWidth(), (int) y + enemies.get(i).getBitmapHeight());
						Log.d(TAG, "rect=" + rect.toShortString());
						enemies.remove(enemies.get(i));
						addExplosion(x, y, rect);
						Log.d(TAG, "enemies.size() = " + enemies.size());
						
					}
				} 
			}
		}
	}
	
	/**
	 * Update Collisions between Enemies Projectiles and SpaceShip
	 */
	private void collisionProjectilesAndSpaceShip() {
		// Collision: Projectiles and Enemies
		for (int i = 0; i < enemyProjectiles.size(); i++) {
			if (enemyProjectiles.get(i).collisionDetection(getSpaceShip())) {	
				// Destroy the projectile
				enemyProjectiles.get(i).setVisible(false);
				
				// Logging how many projectiles there are in the list.
				Log.d(TAG, "projectiles.size() = " + projectiles.size());
					
				// Update the life of the spaceship by the amount that the projectile inflicts.
				lifeBar.setHit(true);
				lifeBar.decreaseLife(enemyProjectiles.get(i).getDamage());
				
				// TODO - TEMPORARY: VIBRATE
				Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
				long milliseconds = 100;
				v.vibrate(milliseconds);
				// VIBRATE
				
				Log.d(TAG, "YOUR LIFE IS: " + lifeBar.getLifeBar());
				// Logging the life of the enemy
				Log.d(TAG, getSpaceShip().getLife() + "");

			} 
		}
	}
	
	/**
	 * Update Collisions between Enemies and screen edges
	 */
	private void collisionEnemiesAndScreenEdges() {
		
		// Collision: Enemies and screen edges
		for (int i = 0; i < enemies.size(); i++) {
//		for (Enemy enemy : enemies) {
			if (enemies.get(i).getX() <= 0 - enemies.get(i).getBitmapWidth()/2 || enemies.get(i).getX() >= getScreenWidth() + enemies.get(i).getBitmapWidth()/2 /* || enemies.get(i).getY() <= 0 - enemies.get(i).getBitmapHeight()/2 */  || enemies.get(i).getY() >= getScreenHeight() + enemies.get(i).getBitmapHeight()/2) {
				enemies.get(i).setVisible(false);
				enemies.remove(enemies.get(i));
				
				// Logging how many enemies there are in the list.
				Log.d(TAG, "enemies.size() = " + enemies.size());

			}
		}
	}	
	
	public void updateLifeBar() {
//		lifeBar.setLifeBar(getSpaceShip().getLife());
		if (lifeBar.isDead()) {
			Log.d(TAG, "YOUR LIFE IS: " + lifeBar.getLifeBar() + "SHOULD BE 0");
			// TODO - TEMPORARY CODE: If spaceship has no life left make it invisible
			// TODO - SUGGESTION: Maybe we could handle "continues" so that a spacship has multiple lifes
			getSpaceShip().setVisible(false); 
			float shipX = getSpaceShip().getX();
			float shipY = getSpaceShip().getY();
			Rect shipExpRect = new Rect((int) shipX, (int) shipY, (int) shipX + getSpaceShip().getBitmapWidth(), (int) shipY + getSpaceShip().getBitmapHeight());
			Log.d(TAG, "rect=" + shipExpRect.toShortString());
			addExplosion(getSpaceShip().getX(), getSpaceShip().getY(), shipExpRect);
			
			// TODO - TEMPORARY CODE: This should stop the gameloop somehow first.
			scoreBundle.putInt("score", scoreCounter.getScore());
			gameOver(scoreBundle);
		}
	}

	// :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	// :::::::::::::::::::::::::::::::::::::::::::::: Rendering ::::::::::::::::::::::::::::::::::::::::::::::
	// :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	@Override
	public void renderLevel(Canvas canvas) {
		renderBackground(canvas);
		renderSpaceShip(canvas);
		renderEnemies(canvas);
		renderProjectiles(canvas);
		renderExplosions(canvas);
		renderStatusBar(canvas);
	}

	private void renderStatusBar(Canvas canvas) {
		lifeBar.draw(canvas);
		scoreCounter.draw(canvas);
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
		for (Projectile enemyprojectile : enemyProjectiles) {
			enemyprojectile.draw(canvas);
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
