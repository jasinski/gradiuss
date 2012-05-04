package com.gradiuss.game.highscore;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gradiuss.game.R;

public class SqlLiteActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	
	Button sqlUpdate, sqlView;
	EditText sqlName, sqlScore;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlliteexample);
        sqlUpdate = (Button) findViewById(R.id.bSQLUpdate);
        sqlName = (EditText) findViewById(R.id.etSQLName);
        sqlScore = (EditText) findViewById(R.id.etSQLHotness);//Det står hotness men borde vara score, kan inte ändra i R..
        
        sqlView = (Button) findViewById(R.id.bSQLOpenView);
        sqlView.setOnClickListener(this);
        sqlUpdate.setOnClickListener(this);
        
    }


	public void onClick(View v) {
		
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.bSQLUpdate:
			boolean didItWork = true;
			try{
			
			String name = sqlName.getText().toString();
			String score = sqlScore.getText().toString();
			
			Highscore entry = new Highscore(SqlLiteActivity.this);
			entry.open();
			entry.createEntry(name, score);
			entry.close();
			}catch (Exception e){
				didItWork = false;
				String error = e.toString();
				Dialog d = new Dialog(this);
				d.setTitle("Dang it!");
				TextView tv = new TextView(this);
				tv.setText("Success");
				d.setContentView(tv);
				d.show();
				
			}finally{
				
				if(didItWork){
					Dialog d = new Dialog(this);
					d.setTitle("Heck Yea!");
					TextView tv = new TextView(this);
					tv.setText("Success");
					d.setContentView(tv);
					d.show();
				}
			}

			
			break;
		case R.id.bSQLOpenView:
			Intent i = new Intent("your.highscore.sqlliteexample.SQLVIEW");
			startActivity(i);
			
			break;
			
		}
	}
}