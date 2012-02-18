package com.WTFGdzieJestem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataDatabaseHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "WTF";
	public static final String TABLE_NAME = "homeLocation";
	public static final String LOCATION_NAME = "locationName";
	public static final String LONGITUDE = "longitude";
	public static final String LATTITUDE = "lattitude";
	

	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "+ TABLE_NAME +" (locationName text not null primary key , "
			+ "longitude double, lattitude double);";
	
	
	public DataDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db,  int oldVersion,
			int newVersion) {
		Log.w(DataDatabaseHelper.class.getName(),
				"Updejt bazy z wersji " + oldVersion + " do wersji "
						+ newVersion + ", co wymaze wszystkie dane");
		db.execSQL("DROP TABLE IF EXISTS person");
		onCreate(db);
	}

}