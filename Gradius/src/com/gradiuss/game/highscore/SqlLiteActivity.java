package com.gradiuss.game.highscore;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gradiuss.game.GameViewActivity;
import com.gradiuss.game.R;
import com.gradiuss.game.StartGameActivity;
import com.gradiuss.game.models.TypeOneProjectile;

public class SqlLiteActivity extends Activity implements OnClickListener {
	private static final String TAG = SqlLiteActivity.class.getSimpleName();
	Button sqlUpdate, sqlMain, sqlRestart;
	EditText sqlName, sqlScore;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Buttons and EditTexts connected to the xml
        setContentView(R.layout.sqlliteexample);
        sqlUpdate = (Button) findViewById(R.id.bSQLUpdate);
        sqlName = (EditText) findViewById(R.id.etSQLName);
        sqlScore = (EditText) findViewById(R.id.etSQLHotness);//Det står hotness men borde vara score, kan inte ändra i R..
        sqlMain = (Button) findViewById(R.id.sqlMain);
        sqlRestart = (Button) findViewById(R.id.sqlRestart);
        
        sqlUpdate.setOnClickListener(this);  
		sqlMain.setOnClickListener(this);
        sqlRestart.setOnClickListener(this);  

        
    }


	public void onClick(View v) {
		
		
		switch (v.getId()){
		// Adding the given values from the editText and inserting them to the table
		case R.id.bSQLUpdate:
			boolean didItWork = true;
			try{
			
			String name = sqlName.getText().toString();
			String score = sqlScore.getText().toString();
			
			Highscore entry = new Highscore(SqlLiteActivity.this);
			entry.open();
			entry.createEntry(name, score);
			Log.d(TAG,entry.getData());
			entry.close();
			
			
			}catch (Exception e){
				didItWork = false;
				String error = e.toString();
				
				
			}finally{
				//Popup messages after inserting a value in the table
				if(didItWork){
					Intent i = new Intent("android.intent.action.SQLVIEWACTIVITY");
					startActivity(i);
				}
			}
			

			
			break;
		case R.id.sqlMain:
			startActivity(new Intent(this, StartGameActivity.class));
			break;
			
		case R.id.sqlRestart:
			startActivity(new Intent(this, GameViewActivity.class));
			break;
			
		}
		
	}
}