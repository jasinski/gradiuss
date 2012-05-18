package com.gradiuss.game.test;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.test.InstrumentationTestCase;

import com.gradiuss.game.models.AlienShip;
import com.gradiuss.game.models.SpaceShip;
import junit.framework.Assert;
import android.graphics.BitmapFactory;


public class AlienShipTest extends InstrumentationTestCase {
	
	SpaceShip testTarget;
	AlienShip testAlien;
	Resources res;
	Bitmap bm, bm1;

	protected void setUp() throws Exception {
		super.setUp();
		res = getInstrumentation().getContext().getResources();
		bm = BitmapFactory.decodeResource(res, R.drawable.attacker);
		bm1 = BitmapFactory.decodeResource(res, R.drawable.spaceship);
		testTarget = new SpaceShip(bm1, 5, 15, 10, 10);
		testAlien = new AlienShip(bm, 5, 5, testTarget);//x, y
	}

	protected void tearDown() throws Exception {
		testTarget = null;
		testAlien = null;
		super.tearDown();
	}

	public void testUpdateState() {
		testAlien.setShooting(false);
		Assert.assertFalse(testAlien.isShooting());
		 //x difference = 0
		float yDiff = testAlien.getY() - testTarget.getY(); //y difference = -10
		Assert.assertEquals(-10, yDiff, 0.2);
		testAlien.setX(7);//
		float xDiff1 = testAlien.getX() - testTarget.getX(); // x Difference = 2
			Assert.assertEquals(2, xDiff1, 0.2);
			//positive xDiff => coming from right
			testAlien.setMoveRight(false);
			testAlien.setMoveLeft(true);
			Assert.assertEquals(true, testAlien.isMovingLeft());
			Assert.assertEquals(false, testAlien.isMovingRight());
		testAlien.setX(3);//
		float xDiff2 = testAlien.getX() - testTarget.getX(); // x Difference = -2
			Assert.assertEquals(-2, xDiff2, 0.2);
			testAlien.setMoveLeft(false);
			testAlien.setMoveRight(true);
			Assert.assertEquals(false, testAlien.isMovingLeft());
			Assert.assertEquals(true, testAlien.isMovingRight());	
		
			testAlien.setShooting(true);
			Assert.assertEquals(true, testAlien.isShooting());
			testAlien.setShooting(false);
			testAlien.setMoveRight(false);
			testAlien.setMoveLeft(false);
			Assert.assertEquals(false, testAlien.isMovingLeft());
			Assert.assertEquals(false, testAlien.isMovingRight());
			Assert.assertEquals(false, testAlien.isShooting());
	}

	public void testSetHit() {
		testAlien.setHit(true);
		Assert.assertEquals(true, testAlien.isHit());
		testAlien.setHit(false);
		Assert.assertEquals(false, testAlien.isHit());
	}

}
