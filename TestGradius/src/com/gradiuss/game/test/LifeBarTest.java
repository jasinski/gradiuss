package com.gradiuss.game.test;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.InstrumentationTestCase;

import com.gradiuss.game.models.LifeBar;

import junit.framework.Assert;

public class LifeBarTest extends InstrumentationTestCase {

	LifeBar testLb;
	Resources res;
	Bitmap bm;
	
	protected void setUp() throws Exception {
		super.setUp();
		res = getInstrumentation().getContext().getResources();
		bm = BitmapFactory.decodeResource(res, R.drawable.life_bar);
		testLb = new LifeBar(bm, 10, 10);
		testLb.setHit(true);
		testLb.setDead(false);
		testLb.setLifeBar(100);
	}
	
	public void testUpdateState() {
		testLb.setLifeBar(100);
		Assert.assertEquals(100, testLb.getLifeBar(), 0);
		testLb.updateState();
		Assert.assertFalse(testLb.isDead());
		testLb.setLifeBar(-10);
		testLb.updateState();
		Assert.assertTrue(testLb.isDead());
	}
	
	public void testHit() {
		testLb.updateState();
		Assert.assertFalse(testLb.isHit());
		testLb.setHit(true);
		Assert.assertTrue(testLb.isHit());
		testLb.updateState();
		Assert.assertFalse(testLb.isHit());
	}

}
