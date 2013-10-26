package edu.gatech.foodies.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
	private SQLiteDatabase myDatabase;
	private final Context myContext;
	private static String DB_NAME = "foodies.db";
	public static String DB_PATH = "";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
		if(android.os.Build.VERSION.SDK_INT >= 4.2) {
			DB_PATH = context.getApplicationInfo().dataDir + "/databases/";         
		}
		else {
			DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
		}
		this.myContext = context;
	}
	
	// If database doesn't exist copy it from assets
	public void createDB() throws IOException {
		boolean dbExists = checkDB();
		if(!dbExists) {
			this.getReadableDatabase();
			this.close();
			try {
				copyDB();
			} catch (IOException e) {
				throw new Error("Error copying DB");
			}
		}
	}
	
	//Check if database exists
	private boolean checkDB() {
		File dbFile = new File(DB_PATH + DB_NAME);
		return dbFile.exists();
	}
	
	//Copy the database from assets
	private void copyDB() throws IOException {
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		String outFile = DB_PATH + DB_NAME;
		OutputStream myOutput = new FileOutputStream(outFile);
		byte[] mBuffer = new byte[1024];
        int len;
        while ((len = myInput.read(mBuffer)) > 0) {
            myOutput.write(mBuffer, 0, len);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
	}

	//Open the database, so we can query it
    public boolean openDB() throws SQLException
    {
        String mPath = DB_PATH + DB_NAME;
        myDatabase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return myDatabase != null;
    }
    
    @Override
    public synchronized void close() 
    {
        if(myDatabase != null)
            myDatabase.close();
        super.close();
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
