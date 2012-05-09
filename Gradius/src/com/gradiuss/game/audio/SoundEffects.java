package com.gradiuss.game.audio;


import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundEffects {

	private SoundPool soundPool;
	private Context context;
	private int projectileTypeOne = 0;
	private int explosion = 0;
	private float shootVolume = 0;
	private float explosionVolume = 0;
	
	
	public SoundEffects(Context context) {
		this.context = context;
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
	}
	
	// Set sound
	public void setShootSound(int resId, float volume) {
		shootVolume = volume;
		projectileTypeOne = soundPool.load(this.context, resId, 1);
	}
	
	public void setExplosionSound(int resId, float volume) {
		explosionVolume = volume;
		explosion = soundPool.load(this.context, resId, 1);
	}
	
	// Play sound
	public void playShootSound() {
		if (projectileTypeOne != 0) {
			soundPool.play(projectileTypeOne, (float) shootVolume, (float) shootVolume, 0, 0, 1); // Volume = 0.01
		}
	}
	
	public void playExplosionSound() {
		if (explosion != 0) {
			soundPool.play(explosion, (float) explosionVolume, (float) explosionVolume, 0, 0, 1); // Volume = 0.25
		}
	}

}
