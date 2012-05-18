package com.gradiuss.game.test;

import junit.framework.Assert;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.test.InstrumentationTestCase;
import com.gradiuss.game.R;
import com.gradiuss.game.models.AlienProjectile;
import com.gradiuss.game.models.Projectile;

public class AlienProjectileTest extends InstrumentationTestCase {
	
	private AlienProjectile testProj;
	private AlienProjectile copyProj;
	private Bitmap bitmap;
	private Resources res;
	private Rect rect;

	protected void setUp() throws Exception {
		super.setUp();
		rect = new Rect(30, 30, 35, 35);
		res = getInstrumentation().getContext().getResources();
		bitmap = BitmapFactory.decodeResource(res, R.drawable.projectile_alien);
		testProj = new AlienProjectile(bitmap, 5, 5, rect);
		copyProj = new AlienProjectile(testProj);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		testProj = null;
		copyProj = null;
	}

	public void testUpdateState() {
		copyProj.setVy(10);
		copyProj.setDirectionY(Projectile.DIRECTION_DOWN);
		copyProj.setMoveDown(true);
		copyProj.updateState();//5 + 1*10 = 15
		Assert.assertEquals(15, copyProj.getY(), 0.2);
		copyProj.updateState();//15 + 1*10 = 25
		copyProj.updateState();//25 + 1*10 = 35
		copyProj.updateState();//35 + 1*10 = 45
		copyProj.updateState();//45 + 1*10 = 55
		copyProj.updateState();//55 + 1*10 = 65
		copyProj.updateState();//65 + 1*10 = 75
		Assert.assertEquals(75, copyProj.getY(), 0);
		copyProj.setVisible(false);
		Assert.assertFalse(copyProj.isVisible());
	}

}
