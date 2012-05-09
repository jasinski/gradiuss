package com.gradiuss.game.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.gradiuss.game.models.Explosion;

public class ExplosionTest extends InstrumentationTestCase {
	
	public static final float STANDARD_FRAME_TIME = 50000000;
	public long previousExplosionFrame = 0;
	Explosion testExp;
	Resources res;
	Bitmap bm1, bm2, bm3, bm4, bm5, bm6, bm7, bm8, bm9;
	List<Bitmap> animations;
	Rect rect;

	protected void setUp() throws Exception {
		super.setUp();
		res = getInstrumentation().getContext().getResources();
		bm1 = BitmapFactory.decodeResource(res, R.drawable.bmexplosion1);
		bm2 = BitmapFactory.decodeResource(res, R.drawable.bmexplosion2);
		bm3 = BitmapFactory.decodeResource(res, R.drawable.bmexplosion3);
		bm4 = BitmapFactory.decodeResource(res, R.drawable.bmexplosion4);
		bm5 = BitmapFactory.decodeResource(res, R.drawable.bmexplosion5);
		bm6 = BitmapFactory.decodeResource(res, R.drawable.bmexplosion6);
		bm7 = BitmapFactory.decodeResource(res, R.drawable.bmexplosion7);
		bm8 = BitmapFactory.decodeResource(res, R.drawable.bmexplosion8);
		bm9 = BitmapFactory.decodeResource(res, R.drawable.bmexplosion9);
		animations = new ArrayList<Bitmap>();
		animations.add(bm1);
		animations.add(bm2);
		animations.add(bm3);
		animations.add(bm4);
		animations.add(bm5);
		animations.add(bm6);
		animations.add(bm7);
		animations.add(bm8);
		animations.add(bm9);
		rect = new Rect(8, 8, 17, 17); //left top right down
		testExp = new Explosion(animations, 5, 5, rect);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		testExp = null;
	}

	public void testUpdateState() {
		testExp.nextAnimation();
		testExp.nextAnimation();
		testExp.nextAnimation();
		testExp.nextAnimation();
		testExp.nextAnimation();
		testExp.nextAnimation();
		testExp.nextAnimation();
		testExp.nextAnimation();
		testExp.nextAnimation();
		int pointer = testExp.getAnimationPointer();
		Log.d("pointer is", Integer.toString(pointer));
	}

	public void testExplosion() {
		Assert.assertTrue(testExp.getAnimationPointer()==0);
		Assert.assertEquals(9, testExp.getAnimations().size());
		Assert.assertEquals(9, testExp.getRectWidth());
		Assert.assertEquals(9, testExp.getRectHeight());
		Assert.assertEquals(5, testExp.getX(), 0.5);
		Assert.assertEquals(5, testExp.getY(), 0.5);
	}

	public void testLastFrame() {
		testExp.nextAnimation();
		testExp.nextAnimation();
		testExp.nextAnimation();
		testExp.nextAnimation();
		testExp.nextAnimation();
		testExp.nextAnimation();
		testExp.nextAnimation();
		testExp.nextAnimation();
		testExp.nextAnimation();
		testExp.nextAnimation();
		Assert.assertTrue(testExp.lastFrame());
	}

}
