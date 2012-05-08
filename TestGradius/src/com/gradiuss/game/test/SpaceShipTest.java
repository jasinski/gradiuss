package com.gradiuss.game.test;

import junit.framework.Assert;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.InstrumentationTestCase;

import com.gradiuss.game.models.MovingObject;
import com.gradiuss.game.models.SpaceShip;



//methods not directly tested are isAlive() isShooting() and get


public class SpaceShipTest extends InstrumentationTestCase {

	SpaceShip testSpSh;
	Resources res;
	Bitmap bm;
	
	protected void setUp() throws Exception {
		super.setUp();
		res = getInstrumentation().getContext().getResources();
		bm = BitmapFactory.decodeResource(res, R.drawable.spaceship);
		testSpSh = new SpaceShip(bm, 5, 5, 10, 10);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		testSpSh = null;
	}
	
	//help method for test 
	private void setMovement(float x, float y, float Vx, float Vy) {
		testSpSh.setX(x);
		testSpSh.setY(y);
		testSpSh.setVx(Vx);
		testSpSh.setVy(Vy);
	}
	
	//help method for test 
	private void setMove(boolean up, boolean right, boolean down, boolean left) {
		testSpSh.setMoveUp(up);
		testSpSh.setMoveRight(right);
		testSpSh.setMoveDown(down);
		testSpSh.setMoveLeft(left);
	}
	
	//help method for test 
	private void reset() {
		testSpSh.setX(0);
		testSpSh.setY(0);
		testSpSh.setVx(0);
		testSpSh.setVy(0);
		setMove(false, false, false, false);
		testSpSh.setLife(0);
		testSpSh.setShooting(false);
		testSpSh.setVisible(true);
	}
	
	//testing constructor
	public void testSpaceShip() {
		Assert.assertNotNull(testSpSh);
		Assert.assertSame(bm, testSpSh.getBitmap());
		Assert.assertEquals(5, testSpSh.getX(), 0);
		Assert.assertEquals(5, testSpSh.getY(), 0);
		Assert.assertEquals(10, testSpSh.getVx(), 0);
		Assert.assertEquals(10, testSpSh.getVy(), 0);
	}

	public void testUpdateState() {
		setMovement(20, 20, 20, 20); //x, y, Vx, Vy.
		testSpSh.setDirectionY(MovingObject.DIRECTION_UP); // -1
		setMove(true, false, false, false); //up
		testSpSh.updateState();
		Assert.assertEquals(20, testSpSh.getX(), 0.5);//nothing happens
		Assert.assertEquals(0, testSpSh.getY(), 0.5);
		reset();
		
		setMovement(20, 20, 20, 20); //x, y, Vx, Vy.
		testSpSh.setDirectionY(MovingObject.DIRECTION_RIGHT); // 1
		setMove(false, true, false, false); //right
		testSpSh.updateState();
		Assert.assertEquals(40, testSpSh.getX(), 0.5);
		Assert.assertEquals(20, testSpSh.getY(), 0.5);//nothing happens
		reset();
		
		setMovement(20, 20, 20, 20); //x, y, Vx, Vy.
		testSpSh.setDirectionY(MovingObject.DIRECTION_DOWN); // 1
		setMove(false, false, true, false); //down
		testSpSh.updateState();
		Assert.assertEquals(20, testSpSh.getX(), 0.5);//nothing happens
		Assert.assertEquals(40, testSpSh.getY(), 0.5);
		reset();
		
		setMovement(20, 20, 20, 20); //x, y, Vx, Vy.
		testSpSh.setDirectionX(MovingObject.DIRECTION_LEFT); // -1
		setMove(false, false, false, true); //left
		Assert.assertTrue(testSpSh.isMovingLeft());
		testSpSh.updateState();
		Assert.assertEquals(0, testSpSh.getX(), 0.5);
		Assert.assertEquals(20, testSpSh.getY(), 0.5);//nothing happens
		reset();
	}

	public void testSetAlive() {
		testSpSh.setLife(100);
		testSpSh.setAlive(true);
		Assert.assertTrue("testSpSh should live.", testSpSh.isAlive());
		reset(); //sets among other things life to 0.
		Assert.assertEquals(true, testSpSh.isAlive());
	}

	public void testSetLife() {
		testSpSh.setLife(15);
		Assert.assertEquals(15, testSpSh.getLife(), 0);
		reset();
	}

	public void testSetShooting() {
		Assert.assertFalse(testSpSh.isShooting());
		testSpSh.setShooting(true);
		Assert.assertTrue(testSpSh.isShooting());
		reset();
	}

}
