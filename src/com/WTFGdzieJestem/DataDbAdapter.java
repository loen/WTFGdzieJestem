package com.WTFGdzieJestem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataDbAdapter {

	private DataDatabaseHelper databaseHelper;
	private SQLiteDatabase database;
	private Context context;
	
	
	
	public DataDbAdapter(Context context) {
		this.context = context;
	}

	public DataDbAdapter open() throws SQLException {
		databaseHelper = new DataDatabaseHelper(context);
		database = databaseHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		databaseHelper.close();
	}
	
	public void deleteData(){
		database.delete(DataDatabaseHelper.DATABASE_NAME,"1",null);
	}
	
	public void saveData(String locationName,double longitude,double lattitude){
		this.deleteData();
		ContentValues contentValues = prepareContent(locationName,longitude, lattitude);
		database.insert(DataDatabaseHelper.TABLE_NAME, null, contentValues);
		
	}
	
	public ContentValues getData(){
		ContentValues contentValues = new ContentValues();
		String [] columns = new String[]{
		DataDatabaseHelper.LOCATION_NAME,
		DataDatabaseHelper.LONGITUDE,
		DataDatabaseHelper.LATTITUDE,};
		Cursor cursor = database.query(DataDatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
		if(cursor.getCount() == 1){
			cursor.moveToFirst();
			contentValues.put(DataDatabaseHelper.LOCATION_NAME, cursor.getString(cursor.getColumnIndex(DataDatabaseHelper.LOCATION_NAME)));
			contentValues.put(DataDatabaseHelper.LONGITUDE, cursor.getDouble(cursor.getColumnIndex(DataDatabaseHelper.LONGITUDE)));
			contentValues.put(DataDatabaseHelper.LATTITUDE, cursor.getDouble(cursor.getColumnIndex(DataDatabaseHelper.LATTITUDE)));
			return contentValues;
		}else{
			return null;
		}
		
		
	}
	
	
	private ContentValues prepareContent(String locationName, double longitude,double lattitude){
		ContentValues contentValues = new ContentValues();
		
		contentValues.put(DataDatabaseHelper.LOCATION_NAME,locationName);
		contentValues.put(DataDatabaseHelper.LONGITUDE,longitude);
		contentValues.put(DataDatabaseHelper.LATTITUDE,lattitude);
				
		return contentValues;
	}
}
