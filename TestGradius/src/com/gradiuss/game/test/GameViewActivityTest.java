package com.gradiuss.game.test;

import junit.framework.Assert;
import android.app.Instrumentation;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ImageButton;

import com.gradiuss.game.GameLoopThread;
import com.gradiuss.game.GameView;
import com.gradiuss.game.GameViewActivity;

public class GameViewActivityTest extends ActivityInstrumentationTestCase2<GameViewActivity> {

	private GameViewActivity gameViewActivity;
    private GameView gameView;
    private GameLoopThread gameLoopThread;
    private Button bLeftPad;
    private Button bRightPad;
    private Button bUpPad;
    private Button bDownPad;
    private Button bFire;
    private ImageButton bChangeWeapon;
    
    private Drawable leftbutton_toggler;
    private Drawable rightbutton_toggler;
    private Drawable downbutton_toggler;
    private Drawable upbutton_toggler;
    private Drawable firebutton_toggler;
    private Drawable change_weaon_temporary_test;
	
	public GameViewActivityTest() {
		super("com.gradiuss.game", GameViewActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		gameViewActivity = this.getActivity();
		gameView = (GameView) gameViewActivity.findViewById(com.gradiuss.game.R.id.gameView);
		gameLoopThread = gameView.gameLoop;
		bLeftPad = (Button) gameViewActivity.findViewById(com.gradiuss.game.R.id.bLeftPad);
		bRightPad = (Button) gameViewActivity.findViewById(com.gradiuss.game.R.id.bRightPad);
		bUpPad = (Button) gameViewActivity.findViewById(com.gradiuss.game.R.id.bUpPad);
		bDownPad = (Button) gameViewActivity.findViewById(com.gradiuss.game.R.id.bDownPad);
		bFire = (Button) gameViewActivity.findViewById(com.gradiuss.game.R.id.bFire);
		bChangeWeapon = (ImageButton) gameViewActivity.findViewById(com.gradiuss.game.R.id.bChangeWeapon);
		
		Resources res = getInstrumentation().getContext().getResources();
		leftbutton_toggler = res.getDrawable(com.gradiuss.game.R.drawable.leftbutton_toggler);
		downbutton_toggler = res.getDrawable(com.gradiuss.game.R.drawable.downbutton_toggler);
		rightbutton_toggler = res.getDrawable(com.gradiuss.game.R.drawable.rightbutton_toggler);
		upbutton_toggler = res.getDrawable(com.gradiuss.game.R.drawable.upbutton_toggler);
		firebutton_toggler = res.getDrawable(com.gradiuss.game.R.drawable.firebutton_toggler);
		change_weaon_temporary_test = res.getDrawable(com.gradiuss.game.R.drawable.change_weaon_temporary_test);
		
	}
	
	public void testPreconditions() {
	      assertNotNull(gameView);
	      assertNotNull(gameLoopThread);
	      assertNotNull(bLeftPad);
	      assertNotNull(bRightPad);
	      assertNotNull(bUpPad);
	      assertNotNull(bDownPad);
	      assertNotNull(bFire);
	      assertNotNull(bChangeWeapon);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
//	public void testDrawables() {
//		assertEquals(leftbutton_toggler, (Drawable) leftbutton_toggler.getCurrent());
//		assertEquals(rightbutton_toggler, (Drawable) rightbutton_toggler.getCurrent());
//		assertEquals(downbutton_toggler, (Drawable) downbutton_toggler.getCurrent());
//		assertEquals(upbutton_toggler, (Drawable) upbutton_toggler.getCurrent());
//		assertEquals(firebutton_toggler, (Drawable) firebutton_toggler.getCurrent());
//		assertEquals(change_weaon_temporary_test, (Drawable) change_weaon_temporary_test.getCurrent());
//	}

	public void testOnResume() {
		Instrumentation mInstr = this.getInstrumentation();
		
		mInstr.callActivityOnPause(gameViewActivity);
		Assert.assertFalse(gameLoopThread.isRunning());
		
		mInstr.callActivityOnResume(gameViewActivity);
		Assert.assertTrue(gameLoopThread.isRunning());
	}

}
