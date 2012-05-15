package com.gradiuss.game.gameover;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gradiuss.game.R;
import com.gradiuss.game.StartGameActivity;



public class GameOverActivity extends Activity implements OnClickListener {

	Button bMain;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gameover);
		bMain = (Button) findViewById(R.id.bMain);
		bMain.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bMain:
			startActivity(new Intent(this, StartGameActivity.class));
			finish();
			break;
		}
	}
	
}
