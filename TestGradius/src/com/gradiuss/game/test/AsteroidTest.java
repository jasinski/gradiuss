package com.gradiuss.game.test;

import junit.framework.Assert;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.InstrumentationTestCase;

import com.gradiuss.game.models.Asteroid;

public class AsteroidTest extends InstrumentationTestCase {
	
	Asteroid testAsteroid;
	Resources res;
	Bitmap bm;

	protected void setUp() throws Exception {
		super.setUp();
		res = getInstrumentation().getContext().getResources();
		bm = BitmapFactory.decodeResource(res, R.drawable.asteroid);
		testAsteroid = new Asteroid(bm, 5, 5);	
		testAsteroid.setMoveDown(false);
		testAsteroid.setMoveUp(false);
		testAsteroid.setMoveLeft(false);
		testAsteroid.setMoveRight(false);
		testAsteroid.setVy(5);
		testAsteroid.setVx(5);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		testAsteroid = null;
	}

	public void testUpdateState() {
		testAsteroid.setMoveDown(true);
		testAsteroid.updateState();//5 + 1*5
		Assert.assertEquals(10, testAsteroid.getY(), 0.2);//y=10
		
		testAsteroid.setMoveUp(true);
		testAsteroid.setMoveDown(false);
		testAsteroid.updateState();
		Assert.assertEquals(5, testAsteroid.getY(), 0.2);//y=5

		testAsteroid.setMoveLeft(true);
		testAsteroid.setMoveUp(false);
		testAsteroid.updateState();
		Assert.assertEquals(0, testAsteroid.getX(), 0.2);//x=0
		
		testAsteroid.setMoveRight(true);
		testAsteroid.setMoveLeft(false);
		testAsteroid.updateState();
		Assert.assertEquals(5, testAsteroid.getX(), 0.2);//x=5

		testAsteroid.setHit(true);
		testAsteroid.setLife(80);
		testAsteroid.updateState();
		Assert.assertEquals(64, testAsteroid.getDamage(), 0.2);
		Assert.assertFalse(testAsteroid.isHit());
		
	}


}
