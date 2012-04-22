package com.gradiuss.game.highscore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Highscore {
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "persons_name";
	public static final String KEY_HIGHSCORE = "persons_highscore";
	
	private static final String DATABASE_NAME = "HighscoreDb";
	private static final String DATABASE_TABLE = "peopleTable";
	private static final int DATABASE_VERSION = 1;
	
	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private static class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE "+ DATABASE_TABLE + " ("+
					KEY_ROWID + " INTEGER PRIMARY KEY, " +
					KEY_NAME + " TEXT NOT NULL, " + 
					KEY_HIGHSCORE + " INTEGER NOT NULL);"
					);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		
		
		
		
	}
	
	public Highscore(Context c){
		ourContext = c;
	}
	
	public Highscore open() throws SQLException{
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	public void close(){
		ourHelper.close();
		
	}

	public long createEntry(String name, String highscore) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, name);
		cv.put(KEY_HIGHSCORE, highscore);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
		
		
	}

	public String getData() {
		// TODO Auto-generated method stub
		String[] columns = new String []{ KEY_NAME, KEY_HIGHSCORE};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, KEY_HIGHSCORE + " DESC", "10");
		
		String result = "";
		
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iName = c.getColumnIndex(KEY_NAME);
		int iHighscore = c.getColumnIndex(KEY_HIGHSCORE);
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			result = result + c.getString(iName) + "                      " + c.getString(iHighscore) 
			+ "\n"; 
			
		}
		
		return result;
	}
	
}
