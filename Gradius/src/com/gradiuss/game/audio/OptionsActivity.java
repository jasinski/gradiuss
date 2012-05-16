package com.gradiuss.game.audio;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.gradiuss.game.R;
import com.gradiuss.game.StartGameActivity;

public class OptionsActivity extends Activity implements
		OnSeekBarChangeListener, OnClickListener {

	private static SeekBar seekBarMusic;
	private static SeekBar seekBarEffects;
	private Button bMain;
//	private static AudioManager am;
	public static float volume;
	public static final String filename = "sharedPreferences";
	SharedPreferences volumePreferences;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		
		bMain = (Button) findViewById(R.id.bMain);
		bMain.setOnClickListener(this);
		
		seekBarMusic = (SeekBar) findViewById(R.id.sbMusicVolume);
//		am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		seekBarMusic.setMax(100);
		seekBarMusic.setProgress(getSharedPreferences(filename, 0).getInt("music_volume", 50));
		seekBarMusic.setOnSeekBarChangeListener(this);
		
		seekBarEffects = (SeekBar) findViewById(R.id.sbEffectsVolume);
		seekBarEffects.setMax(100);
		seekBarEffects.setProgress(getSharedPreferences(filename, 0).getInt("effects_volume", 50));
		seekBarEffects.setOnSeekBarChangeListener(this);
	}

	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		
		volumePreferences = getSharedPreferences(filename, 0);
		Editor volumeEditor = volumePreferences.edit();
		
		switch (seekBar.getId()) {
		case R.id.sbMusicVolume:
			volumeEditor.putInt("music_volume", progress);
			Log.d("TAG", "music_volume=" + progress);
			volumeEditor.commit();
			break;
		case R.id.sbEffectsVolume:
			volumeEditor.putInt("effects_volume", progress);
			Log.d("TAG", "effects_volume=" + progress);
			volumeEditor.commit();
			break;
		}		
		
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bMain:
			startActivity(new Intent(this, StartGameActivity.class));
			finish();
			break;
		}
	}
}
