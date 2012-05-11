package com.gradiuss.game.audio;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.gradiuss.game.R;

public class OptionsActivity extends Activity implements
		OnSeekBarChangeListener {

	private static SeekBar seekBarMusic;
	private static SeekBar seekBarEffects;
//	private static AudioManager am;
	public static float volume;
	public static final String filename = "sharedPreferences";
	SharedPreferences volumePreferences;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		seekBarMusic = (SeekBar) findViewById(R.id.seekBar1);
//		am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		seekBarMusic.setMax(100);
		seekBarMusic.setProgress(getSharedPreferences(filename, 0).getInt("music_volume", 50));
		seekBarMusic.setOnSeekBarChangeListener(this);
		
		seekBarEffects = (SeekBar) findViewById(R.id.seekBar2);
		seekBarEffects.setMax(100);
		seekBarEffects.setProgress(getSharedPreferences(filename, 0).getInt("effects_volume", 50));
		seekBarEffects.setOnSeekBarChangeListener(this);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		
		volumePreferences = getSharedPreferences(filename, 0);
		Editor volumeEditor = volumePreferences.edit();
		
		switch (seekBar.getId()) {
		case R.id.seekBar1:
			volumeEditor.putInt("music_volume", progress);
			Log.d("TAG", "music_volume=" + progress);
			volumeEditor.commit();
			break;
		case R.id.seekBar2:
			volumeEditor.putInt("effects_volume", progress);
			Log.d("TAG", "effects_volume=" + progress);
			volumeEditor.commit();
			break;
		}		
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}
}
