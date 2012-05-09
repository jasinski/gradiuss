package com.gradiuss.game.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import com.gradiuss.game.models.Background;
import com.gradiuss.game.models.ParallaxBackground;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.InstrumentationTestCase;
import android.util.Log;

public class ParallaxBackgroundTest extends InstrumentationTestCase {
	List<Background[]> bitmaps;
	float[] speeds;
	float screenHeight;
	float screenWidth;
	int nrOfBackgroundCopies;
	int tmp = 1;
	Resources res;
	Bitmap bm;
	ParallaxBackground testPBg;

	protected void setUp() throws Exception {
		super.setUp();
		screenHeight = 666;
		screenWidth = 666;
		testPBg = new ParallaxBackground(screenHeight, screenWidth);
		testPBg.setVisible(true);
		bitmaps = new ArrayList<Background[]>();
		res = getInstrumentation().getContext().getResources();
		bm = BitmapFactory.decodeResource(res, R.drawable.spelbakgrundnypng);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		testPBg = null;
	}

	public void testUpdateState() {
		
		//Log.d("The integers value is =", Integer.toString(bm.getHeight())); //bm.getHeight() = 333
		int nrOfBgImages = (int) Math.ceil(screenHeight/bm.getHeight()) + 1; //calculateBgImages(int bitmapHeight)
		// screenHeight/bm.getHeight() + 1 = 666/333 + 1
		Assert.assertEquals(3, nrOfBgImages, 0.5); //Test calculateBgImages(int bitmapHeight)
		
		//Test AddBackground(Bitmap bm, int movementSpeed).
		Background[] bgArray = new Background[nrOfBgImages];
		for (int i = 0; i < bgArray.length; i++) {
			bgArray[i] = new Background(bm, 3, 3); 
			bgArray[i].setBitmap(bm);
			bgArray[i].setMovementSpeed(3); //Maybe not needed since Movement is set in constructor of Background?
			bgArray[i].setX(screenWidth/2);
			bgArray[i].setY(screenHeight - i*bgArray[i].getBitmap().getHeight()); //666 - i*333 (i = 0,1,2)
		} //The array of Background don't need testing since the Background class already has been tested
		bitmaps.add(bgArray);
		Assert.assertEquals(bgArray, bitmaps.get(0));
		Assert.assertEquals(666, bgArray[0].getY(), 0.5);
		Assert.assertEquals(333, bgArray[1].getY(), 0.5);
		Assert.assertEquals(0, bgArray[2].getY(), 0.5);
		
		//Test of UpdateState() is not possible since the 
		for (Background[] Bgarray : bitmaps) { //only 1 Background array!
			for (Background background : Bgarray) {
				background.setY(background.getY() + background.getMovementSpeed());	
				//setY[0] = 666 + 3 (669)
				//setY[1] = 333 + 3 (336)
				//setY[2] = 0 + 3 (3)
				if (background.getY() >= 2*screenHeight) {
					background.setY(background.getY() - background.getRedrawPosition()); 
				}
			}
			Assert.assertEquals(669, Bgarray[0].getY(), 0.5);
			Assert.assertEquals(336, Bgarray[1].getY(), 0.5);
			Assert.assertEquals(3, Bgarray[2].getY(), 0.5);
		}

	}

	public void testDraw() {
		for (Background[] bgArray : bitmaps) {
			for (Background background : bgArray) {
				if (testPBg.isVisible()) {
					Assert.assertEquals(bm, background.getBitmap());
				}
			}
		}
	}

	public void testParallaxBackground() {
		Assert.assertEquals(666, testPBg.getScreenHeight(), 0.5);	
	}

	public void testSetScreenHeight() {
		Assert.assertEquals(666, testPBg.getScreenHeight(), 0.5);
	}

	public void testRemoveBackground() {
		// Tested in GameObjectTest
		
	}

	public void testSetSpeed() {
		//TODO Not implemented yet!!!
	}

}
