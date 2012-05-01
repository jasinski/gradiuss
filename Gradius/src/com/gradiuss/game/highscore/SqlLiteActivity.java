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
import com.gradiuss.game.models.TypeOneProjectile;

public class SqlLiteActivity extends Activity implements OnClickListener {
	private static final String TAG = SqlLiteActivity.class.getSimpleName();
	Button sqlUpdate, sqlView;
	EditText sqlName, sqlHotness;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlliteexample);
        sqlUpdate = (Button) findViewById(R.id.bSQLUpdate);
        sqlName = (EditText) findViewById(R.id.etSQLName);
        sqlHotness = (EditText) findViewById(R.id.etSQLHotness);
        
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
			String hotness = sqlHotness.getText().toString();
			
			Highscore entry = new Highscore(SqlLiteActivity.this);
			entry.open();
			entry.createEntry(name, hotness);
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