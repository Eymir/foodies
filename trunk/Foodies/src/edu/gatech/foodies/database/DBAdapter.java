package edu.gatech.foodies.database;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import edu.gatech.foodies.vo.*;

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
			myHelper.createDB();
		} catch (IOException e) {
			throw new Error("Can't create database.");
		}
		return this;
	}
	
	public DBAdapter openDB() throws SQLException {
		try {
			myHelper.openDB();
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
	
	public ArrayList<Recipe> getInstructionByRecipe(String recipe_name) {
		ArrayList<Recipe> result = new ArrayList<Recipe>();
		try {
			String sql = "select Instruction from Recipe_Info where Name = '" + recipe_name +"'";
			Cursor myCur = myDB.rawQuery(sql, null);
			if(myCur != null) {
				myCur.moveToFirst();
				while(!myCur.isAfterLast()) {
					Recipe r = cursorToRecipe(myCur);
					result.add(r);
					myCur.moveToNext();
				}
			}
			myCur.close();
			return result;
		} catch (SQLException e) {
			throw e;
		}
	}
	
	private Recipe cursorToRecipe(Cursor cur) {
		Recipe r = new Recipe();
		r.setName(cur.getString(0));
		r.setIngredients(cur.getString(1));
		r.setTime(cur.getInt(2));
		r.setServings(cur.getInt(3));
		r.setInstruction(cur.getString(4));
		return r;
	}
}
