package com.gradiuss.game.test;

import android.test.InstrumentationTestCase;

import com.gradiuss.game.GameView;

public class GameViewTest extends InstrumentationTestCase {

    private GameView gameView;
	
	protected void setUp() throws Exception {
		gameView = (GameView) gameViewActivity.findViewById(com.gradiuss.game.R.id.gameView);
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSurfaceCreated() {
		fail("Not yet implemented");
	}

	public void testSurfaceChanged() {
		fail("Not yet implemented");
	}

	public void testSurfaceDestroyed() {
		fail("Not yet implemented");
	}

	public void testInitBackground() {
		fail("Not yet implemented");
	}

	public void testInitGameObjects() {
		fail("Not yet implemented");
	}

	public void testUpdateState() {
		fail("Not yet implemented");
	}

	public void testUpdateProjectiles() {
		fail("Not yet implemented");
	}

	public void testAddProjectile() {
		fail("Not yet implemented");
	}

	public void testChangeWeapon() {
		fail("Not yet implemented");
	}

	public void testUpdateCollisions() {
		fail("Not yet implemented");
	}

	public void testRenderState() {
		fail("Not yet implemented");
	}

	public void testRenderBackground() {
		fail("Not yet implemented");
	}

	public void testRenderSpaceShip() {
		fail("Not yet implemented");
	}

	public void testRenderProjectiles() {
		fail("Not yet implemented");
	}

	public void testRenderEnemies() {
		fail("Not yet implemented");
	}

	public void testRenderExplosions() {
		fail("Not yet implemented");
	}

}
