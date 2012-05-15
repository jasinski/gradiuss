package com.gradiuss.game.audio;

import com.gradiuss.game.R;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SeekBarVolume extends Activity implements OnSeekBarChangeListener {

	private static SeekBar sb;
	private static AudioManager am;
	//public static float volume = sb.getProgress();


	   @Override
	    public void onCreate(Bundle savedInstanceState)
	    {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.options);
	        sb = (SeekBar)findViewById(R.id.seekBar1); 
	        am =(AudioManager)getSystemService(Context.AUDIO_SERVICE);
	        int maxV =am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	        int curV =am.getStreamVolume(AudioManager.STREAM_MUSIC);
	        sb.setMax(maxV);
	        sb.setProgress(curV);
	        sb.setOnSeekBarChangeListener(this);
	        //volume = sb.getProgress();
	        

	    }
		  @Override
		    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		       am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
		       Log.d("TEST", "Progress changed! Progress = " + progress);
		      // volume = ((float)progress)/100;
		      //.setVolume(volume);
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
