package com.gradiuss.game.test;

import junit.framework.Assert;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.test.InstrumentationTestCase;
import com.gradiuss.game.models.Projectile;
import com.gradiuss.game.models.TypeOneProjectile;

public class TypeOneProjectileTest extends InstrumentationTestCase {

	private TypeOneProjectile testTypeOneProjectile;
	private Bitmap bitmap1;
	private Bitmap bitmap2;
	private Resources res;
	private Rect rect;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		rect = new Rect(30, 30, 35, 35);
		
		res = getInstrumentation().getContext().getResources();
		bitmap1 = BitmapFactory.decodeResource(res, R.drawable.projectile1);
		bitmap2 = BitmapFactory.decodeResource(res, R.drawable.projectile2);
		
		testTypeOneProjectile = new TypeOneProjectile(bitmap1, 50, 60, rect, 13);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testUpdateState() {
		testTypeOneProjectile.setVy(10);
		testTypeOneProjectile.setDirectionY(Projectile.DIRECTION_UP);
		testTypeOneProjectile.setMoveUp(true);
		testTypeOneProjectile.updateState();
		Assert.assertEquals(50, testTypeOneProjectile.getY(), 0);
		testTypeOneProjectile.updateState();
		testTypeOneProjectile.updateState();
		testTypeOneProjectile.updateState();
		testTypeOneProjectile.updateState();
		testTypeOneProjectile.updateState();
		testTypeOneProjectile.updateState();
		Assert.assertEquals(-10, testTypeOneProjectile.getY(), 0);
		Assert.assertFalse(testTypeOneProjectile.isVisible());
	}

	public void testTypeOneProjectileProjectile() {
		Assert.assertNotNull(testTypeOneProjectile);
		
		TypeOneProjectile testProjetile = new TypeOneProjectile(testTypeOneProjectile);
		
		Assert.assertNotNull(testProjetile);
		
		Assert.assertEquals(bitmap1, testProjetile.getBitmap());
		Assert.assertEquals(50, testProjetile.getX(), 0);
		Assert.assertEquals(60, testProjetile.getY(), 0);
		Assert.assertEquals(13, testProjetile.getDamage(), 0);
	}

}
