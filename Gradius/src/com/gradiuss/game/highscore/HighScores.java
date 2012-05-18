package com.gradiuss.game.highscore;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HighScores {
	private static final String TAG = HighScores.class.getSimpleName();

	// The column names in the table
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "persons_name";
	public static final String KEY_HIGHSCORE = "persons_highscore";
	// Database name
	private static final String DATABASE_NAME = "HighscoreDb";
	// The table name
	private static final String DATABASE_TABLE = "peopleTable";
	private static final int DATABASE_VERSION = 1;

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	// An inner class for creating the Database
	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);

		}

		// Creating the Database table
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT NOT NULL, " + KEY_HIGHSCORE + " INTEGER NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}

	}// Inner class ends

	// The constructor
	public HighScores(Context c) {
		ourContext = c;
	}

	// Opening the helper object
	public HighScores open() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	/**
	 * closing the helper object.
	 */
	public void close() {
		ourHelper.close();
	}

	/**
	 * Inserting values into the table.
	 * @param name
	 * @param highscore
	 * @return 
	 */
	public long createEntry(String name, String highscore) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, name);
		cv.put(KEY_HIGHSCORE, highscore);
		Log.d(TAG, (String) cv.get(KEY_NAME));
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	/**
	 * Fetching data from a table.
	 * @return
	 */
	public ArrayList<String[]> getData() {

		String[] columns = new String[] { KEY_NAME, KEY_HIGHSCORE };

		// How the select statement should be presented
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, KEY_HIGHSCORE + " DESC", "10");

		ArrayList<String[]> result = new ArrayList<String[]>();
		

		int iRow = c.getColumnIndex(KEY_ROWID);
		int iName = c.getColumnIndex(KEY_NAME);
		int iHighscore = c.getColumnIndex(KEY_HIGHSCORE);
				
		// Looping threw all of the rows and storing them in a string
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			String[] temp = new String[2];
			temp[0] = c.getString(iName);
			temp[1] = c.getString(iHighscore);
			result.add(temp);

		}
		return result;
	}

}
