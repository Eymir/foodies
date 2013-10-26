package edu.gatech.foodies.database;

import java.io.IOException;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBAdapter {
	
	private final Context myContext;
	private SQLiteDatabase myDB;
	private DBHelper myHelper;
	
	public DBAdapter(Context context) {
		this.myContext = context;
		myHelper = new DBHelper(myContext);
	}
	
	public DBAdapter createDB() throws SQLException {
		try {
			myHelper.createDatabase();
		} catch (IOException e) {
			throw new Error("Can't create database.");
		}
		return this;
	}
	
	public DBAdapter openDB() throws SQLException {
		try {
			myHelper.openDatabase();
			myHelper.close();
			myDB = myHelper.getReadableDatabase();
		} catch (SQLException e) {
			throw e;
		}
		return this;
	}
	
	public void closeDB() {
		myHelper.close();
	}
	
	public void testDB() {
		String sql = "select * from Recipe_Info";
		Cursor mCur = myDB.rawQuery(sql, null);
		
		if (mCur!=null)
        {
           mCur.moveToNext();
        }
		
		Log.v("Test",mCur.getString(0));
	}
}
