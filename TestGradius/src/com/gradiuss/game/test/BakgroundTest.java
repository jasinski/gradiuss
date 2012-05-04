package com.gradiuss.game.test;

import junit.framework.Assert;

import com.gradiuss.game.models.Background;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.InstrumentationTestCase;


public class BakgroundTest extends InstrumentationTestCase {
	float x;
	float y;
	Bitmap bm;
	int movementSpeed;
	int redrawPosition;
	int nrOfCopies;
	Resources res;
	Background testBg;

	protected void setUp() throws Exception {
		super.setUp();
		res = getInstrumentation().getContext().getResources();
		bm = BitmapFactory.decodeResource(res, R.drawable.spelbakgrundnypng);
		movementSpeed = 3;
		nrOfCopies = 3;
		redrawPosition = 1;
		testBg = new Background(bm, movementSpeed, nrOfCopies);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		testBg = null;
	}

	public void testBackground() {
		Assert.assertNotNull(testBg);
		Assert.assertSame(bm, testBg.getBitmap());
	}

	public void testSetBitmap() {
		Bitmap differentBm = BitmapFactory.decodeResource(res, R.drawable.attacker);
		testBg.setBitmap(differentBm);
		Assert.assertNotNull(testBg.getBitmap()); 
		Assert.assertNotSame(bm, testBg.getBitmap()); //Test getBitmap()
	}

	public void testSetY() {
		testBg.setY(6);
		Assert.assertEquals(6, testBg.getY(), 0.5); //Test getY()
		testBg.setY(-4);
		Assert.assertEquals(-4, testBg.getY(), 0.5); //Test getY()
	}

	public void testSetX() {
		testBg.setX(7);
		Assert.assertEquals(7, testBg.getX(), 0.5); //Test getY()
		testBg.setX(-1);
		Assert.assertEquals(-1, testBg.getX(), 0.5); //Test getY()
	}

	public void testSetMovementSpeed() {
		testBg.setMovementSpeed(50);
		Assert.assertEquals(50, testBg.getMovementSpeed()); //Test getMovementSpeed()
		testBg.setMovementSpeed(-4);
		Assert.assertEquals(-4, testBg.getMovementSpeed()); //Test getMovementSpeed()
	}

	public void testSetRedrawPosition() {
		testBg.setRedrawPosition(40);
		Assert.assertEquals(40, testBg.getRedrawPosition()); //Test getRedrawPosition()
		testBg.setRedrawPosition(-10);
		Assert.assertEquals(-10, testBg.getRedrawPosition()); //Test getRedrawPosition()
	}

}
