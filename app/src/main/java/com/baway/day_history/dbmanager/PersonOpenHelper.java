package com.baway.day_history.dbmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersonOpenHelper extends SQLiteOpenHelper {

	public PersonOpenHelper(Context context) {
		super(context, "person.db", null, 1);
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("create table person (id integer primary key autoincrement,title varchar(20),e_id varchar(20),url varchar(20),data_time varchar(20))");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


	}

}
