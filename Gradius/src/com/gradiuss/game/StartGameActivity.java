package com.gradiuss.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartGameActivity extends Activity implements View.OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startgame);
         
        Button bStartGame = (Button) findViewById(R.id.bStartGame);
        Button bOptions = (Button) findViewById(R.id.bOptions);
        Button bHighScore = (Button) findViewById(R.id.bHighScore);
        bStartGame.setOnClickListener(this);
        bOptions.setOnClickListener(this);
        bHighScore.setOnClickListener(this);

    }

	public void onClick(View v) {
		Toast tmpToast;
		switch (v.getId()) {
		case R.id.bStartGame:
			startActivity(new Intent("android.intent.action.GAMEVIEWACTIVITY"));
			break;
		case R.id.bOptions:
			tmpToast = Toast.makeText(this, "Options Screen! (Not Working yet!)", Toast.LENGTH_SHORT);
			tmpToast.show();
			showOptions(null);
			break;
		case R.id.bHighScore:
			startActivity(new Intent("android.intent.action.SQLVIEWACTIVITY"));
			break;
		}
	}

	private void showOptions(Intent showOptionsIntent) {
		// TODO Auto-generated method stub
	}
	
}











