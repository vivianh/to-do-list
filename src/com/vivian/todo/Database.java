package com.vivian.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper{

	private static Database mInstance;
	
	private static final String TABLE_NAME = "tasks";
	protected static final String COLUMN_ID = "_id";
	protected static final String COLUMN_TASK = "task_name";
	
	private static final String CREATE_TABLE = "CREATE TABLE " 
								+ TABLE_NAME + " (" + COLUMN_ID 
								+ " integer primary key autoincrement, " 
								+ COLUMN_TASK + " TEXT);"; 
	private static final String DATABASE_NAME = "tasks.db";
	private static final int DATABASE_VERSION = 1;

	public static Database getInstance(Context context) {
		
		if (mInstance == null) {
			mInstance = new Database(context.getApplicationContext());
		}
		
		return mInstance;
	}
	
	private Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, 
			int newVersion) {
	}
	
	public void addTask(String task) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_TASK, task);
		db.insert(TABLE_NAME, null, values);
		db.close();
	}
	
	public void deleteTask(int id) {
		// Log.v("vivtalk", "id is " + id);
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, COLUMN_ID + " = ?", 
				new String[] {String.valueOf(id)});
		db.close();
	}
	
	public Cursor getCursor() {
		String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID;
		SQLiteDatabase db = this.getReadableDatabase();
		return db.rawQuery(selectQuery, null);
	}

}
