package com.gradiuss.game.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.test.InstrumentationTestCase;

import com.gradiuss.game.models.MovingObject;
import com.gradiuss.game.models.Projectile;



public class ProjectileTest extends InstrumentationTestCase {
	
	Projectile testProjectile1;
	Projectile testProjectile2;
	Rect rect;
	Resources res;
	Bitmap bitmap1;
	Bitmap bitmap2;
	List<Bitmap> listBitmaps;

	public void setUp() throws Exception {
		testProjectile1 = new Projectile();
		testProjectile2 = new Projectile();
		rect = new Rect(30, 30, 35, 35);
	
		res = getInstrumentation().getContext().getResources();
		bitmap1 = BitmapFactory.decodeResource(res, R.drawable.projectile1);
		bitmap2 = BitmapFactory.decodeResource(res, R.drawable.projectile2);
		
		listBitmaps = new ArrayList<Bitmap>();
		listBitmaps.add(bitmap1);
		listBitmaps.add(bitmap2);
		
		testProjectile1.setAnimations(listBitmaps);
		
		super.setUp();
	}

	public void testProjectileConstructors() {
		Projectile projectile1 = new Projectile();
		Assert.assertNotNull(projectile1);
		
		Projectile projectile2 = new Projectile(projectile1);
		Assert.assertNotNull(projectile2);
		
		Projectile projectile3 = new Projectile(bitmap1, 40, 10);
		Assert.assertEquals(bitmap1, projectile3.getBitmap());
		Assert.assertEquals(40, projectile3.getX(), 0);
		Assert.assertEquals(10, projectile3.getY(), 0);
	}
	
	public void tearDown() throws Exception {
		testProjectile1 = null;
		testProjectile2 = null;
		super.tearDown();
	}
	
	public void testGetDamage() {
		testProjectile1.setDamage(25);
		Assert.assertEquals(25, testProjectile1.getDamage());
		
		testProjectile1.setDamage(-100);
		Assert.assertEquals(-100, testProjectile1.getDamage());
	}
	
	public void testGetFireInterval() {
		testProjectile1.setFireInterval(Projectile.FIRE_TIME_STANDARD/2); // 50000000 är expected
		Assert.assertEquals(Projectile.FIRE_TIME_STANDARD/2, testProjectile1.getFireInterval(), 0);
	}

	public void testIsMovingLeft() {
		testProjectile1.setMoveLeft(true);
		Assert.assertEquals(true, testProjectile1.isMovingLeft());

	}

	public void testIsMovingRight() {
		testProjectile1.setMoveRight(true);
		Assert.assertEquals(true, testProjectile1.isMovingRight());
	}

	public void testIsMovingUp() {
		testProjectile1.setMoveUp(true);
		Assert.assertEquals(true, testProjectile1.isMovingUp());
	}
	
	public void testIsMovingDown() {
		testProjectile1.setMoveDown(true);
		Assert.assertEquals(true, testProjectile1.isMovingDown());
	}
	
	public void testGetVx() {
		testProjectile1.setVx(30);
		Assert.assertEquals(30, testProjectile1.getVx(), 0);
	}
	
	public void testGetVy() {
		testProjectile1.setVy(20);
		Assert.assertEquals(20, testProjectile1.getVy(), 0);
	}
	
	public void testGetDirectionX() {
		testProjectile1.setDirectionX(MovingObject.DIRECTION_LEFT); // -1 är expected
		Assert.assertEquals(MovingObject.DIRECTION_LEFT, testProjectile1.getDirectionX());
	}
	
	public void testGetDirectionY() {
		testProjectile1.setDirectionY(MovingObject.DIRECTION_DOWN); // 1 är expected
		Assert.assertEquals(MovingObject.DIRECTION_DOWN, testProjectile1.getDirectionY());
	}
	
	public void testGetX() {
		testProjectile1.setX(10); // 10 är expected
		Assert.assertEquals(10, testProjectile1.getX(), 0);
	}
	
	public void testGetY() {
		testProjectile1.setY(40); // 40 är expected
		Assert.assertEquals(40, testProjectile1.getY(), 0);
	}
	
	public void testGetRectWidth() {
//		fail("Not yet implemented");
		testProjectile1.setRect(new Rect(10, 10, 30, 30));
		Assert.assertEquals(20, testProjectile1.getRectWidth());
	}
	
	public void testGetRectHeight() {
//		fail("Not yet implemented");
		testProjectile1.setRect(new Rect(10, 10, 30, 30));
		Assert.assertEquals(20, testProjectile1.getRectHeight());
	}
	
	public void testCollisionDetection() {
		Rect testRect1 = new Rect();
		testRect1.left = 10;
		testRect1.right = 20;
		testRect1.top = 10;
		testRect1.bottom = 20;
		Rect testRect2 = new Rect(testRect1);
		
		testProjectile1.setRect(testRect1);
		testProjectile2.setRect(testRect2);
		
		// Assert: Should collide
		Assert.assertTrue(testProjectile1.collisionDetection(testProjectile2));
		
		testRect1.left = 40;
		testRect1.right = 60;
		testRect1.top = 40;
		testRect1.bottom = 60;
		
		// Assert: Should not collide
		Assert.assertFalse(testProjectile1.collisionDetection(testProjectile2));
	}
	
	public void testGetBitmapInt() {
		Assert.assertSame(bitmap1, testProjectile1.getBitmap(0));
		Assert.assertNotNull(testProjectile1.getBitmap(0));
		Assert.assertNotNull(testProjectile1.getBitmap(1));
	}
	
	public void testGetAnimations() {		
		testProjectile1.getAnimations();
		Assert.assertSame(listBitmaps, testProjectile1.getAnimations());
		Assert.assertNotNull(testProjectile1.getAnimations().get(0));
		Assert.assertNotNull(testProjectile1.getAnimations().get(1));
	}
	
	public void testGetAnimationPointer() {
		testProjectile1.setAnimationPointer(3); // 3 är expected
		Assert.assertEquals(3, testProjectile1.getAnimationPointer());

	}
	
	public void testNextAnimation() {
		testProjectile1.setAnimationPointer(0);
		
		Assert.assertEquals(0, testProjectile1.getAnimationPointer());
		Assert.assertEquals(bitmap1, testProjectile1.getBitmap(testProjectile1.getAnimationPointer()));
		
		testProjectile1.nextAnimation();
		
		Assert.assertEquals(1, testProjectile1.getAnimationPointer());
		Assert.assertEquals(bitmap2, testProjectile1.getBitmap(testProjectile1.getAnimationPointer()));
	}
	
	public void testPreviousAnimation() {
		testProjectile1.setAnimationPointer(1);
		
		Assert.assertEquals(1, testProjectile1.getAnimationPointer());
		Assert.assertEquals(bitmap2, testProjectile1.getBitmap(testProjectile1.getAnimationPointer()));
		
		testProjectile1.previousAnimation();
		
		Assert.assertEquals(0, testProjectile1.getAnimationPointer());
		Assert.assertEquals(bitmap1, testProjectile1.getBitmap(testProjectile1.getAnimationPointer()));
	}
	
	public void testGetRect() {
		testProjectile1.setRect(rect);
		Assert.assertSame(rect, testProjectile1.getRect());
	}
	
	public void testIsVisible() {
		testProjectile1.setVisible(true);
		Assert.assertEquals(true, testProjectile1.isVisible());
	}
	
	public void testGetBitmapHeight() {
		testProjectile1.setBitmap(0, bitmap1);
		Assert.assertEquals(bitmap1.getHeight(), testProjectile1.getBitmapHeight());
	}
	
	public void testGetBitmapWidth() {
		testProjectile1.setBitmap(0, bitmap1);
		Assert.assertEquals(bitmap1.getWidth(), testProjectile1.getBitmapWidth());
	}
	
	public void testGetBitmap() {
		testProjectile1.setAnimationPointer(1);
		Assert.assertEquals(bitmap2, testProjectile1.getBitmap());
	}
	
	public void testAddAnimation() {
		testProjectile1.addAnimation(bitmap1);
		assertEquals(3, testProjectile1.getAnimations().size());
	}

}
