package com.gradiuss.game.audio;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

public class GameMusic {
	
	private static MediaPlayer mediaPlayer;
	private static Context musicContext;
	
	public static void setContext(Context context) {
		musicContext = context;
	}
	
	public static void setMusic(int resId) {
		if (mediaPlayer != null) {
			mediaPlayer.reset(); 
		}
		mediaPlayer = MediaPlayer.create(musicContext, resId);
	}
	
	public static void playMusic() {
		mediaPlayer.start();
	}
	
	public static void loopMusic(boolean loop) {
		mediaPlayer.setLooping(loop);
	}
	
	public static void pauseMusic() {
		mediaPlayer.pause();
	}
	
	public static void stopMusic() {
		mediaPlayer.stop();
	}
	
	public static void setVolume(float volume) {
		mediaPlayer.setVolume(volume, volume);
	}

}
