package edu.gatech.foodies.database;

import java.io.IOException;
import java.util.ArrayList;

import edu.gatech.foodies.vo.Recipe;
import edu.gatech.foodies.vo.SQL_args;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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

	public ArrayList<Recipe> getRecipe(SQL_args... args) {
		try {
			ArrayList<Recipe> result = new ArrayList<Recipe>();
			String sql = "";
			ArrayList<String> type = new ArrayList<String>();
			for(SQL_args a : args) {
				String attr = a.getAttr();
				String value = a.getValue();
				if(attr.equals("Ingredients")) {
					sql = sql + "select * from Recipe where Ingredients LIKE '%"+value+"%'";
					sql = sql + " intersect ";
				} else if(attr.equals("Time")) {
					sql = sql + "select * from Recipe where Time <= '"+value+"'";
					sql = sql + " intersect ";
				} else if(attr.equals("Type")) {
					type.add(value);
				} else {
					sql = sql + "select * from Recipe where "+attr+" = '"+value+"'";
					sql = sql + " intersect ";
				}
			}
			sql = sql + "select * from Recipe where Type in (";
			for(int i = 0; i < type.size(); i++) {
				sql = sql + "'"+type.get(i)+"',"; 
			}
			sql = sql.substring(0, sql.length()-1) + ")";
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
	
	public ArrayList<String> getInPicsName() {
		try {
			ArrayList<String> result = new ArrayList<String>();
			String sql = "select Name from Pics_In";
			Cursor myCur = myDB.rawQuery(sql, null);
			if(myCur != null) {
				myCur.moveToFirst();
				while(!myCur.isAfterLast()) {
					String s = myCur.getString(0);
					result.add(s);
					myCur.moveToNext();
				}
			}
			myCur.close();
			return result;
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public ArrayList<String> getTypePicsName() {
		try {
			ArrayList<String> result = new ArrayList<String>();
			String sql = "select Name from Pics_Type";
			Cursor myCur = myDB.rawQuery(sql, null);
			if(myCur != null) {
				myCur.moveToFirst();
				while(!myCur.isAfterLast()) {
					String s = myCur.getString(0);
					result.add(s);
					myCur.moveToNext();
				}
			}
			myCur.close();
			return result;
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public ArrayList<String> getInPics(String value) {
		ArrayList<String> result = new ArrayList<String>();
		try {
			String sql = "select "+value+" from Pics_In where "+value+" is not null";
			Cursor myCur = myDB.rawQuery(sql, null);
			if(myCur != null) {
				myCur.moveToFirst();
				while(!myCur.isAfterLast()) {
					String s = myCur.getString(0);
					result.add(s);
					myCur.moveToNext();
				}
			}
			myCur.close();
			return result;
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public ArrayList<String> getTypePics(String value) {
		ArrayList<String> result = new ArrayList<String>();
		try {
			String sql = "select "+value+" from Pics_Type where "+value+" is not null";
			Cursor myCur = myDB.rawQuery(sql, null);
			if(myCur != null) {
				myCur.moveToFirst();
				while(!myCur.isAfterLast()) {
					String s = myCur.getString(0);
					result.add(s);
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
		r.setType(cur.getString(0));
		r.setName(cur.getString(1));
		r.setIngredients(cur.getString(2));
		r.setTime(cur.getInt(3));
		r.setServings(cur.getInt(4));
		r.setInstruction(cur.getString(5));
		return r;
	}
}
