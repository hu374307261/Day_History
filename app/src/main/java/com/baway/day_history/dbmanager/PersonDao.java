package com.baway.day_history.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.baway.day_history.bean.Persontos;

import java.util.ArrayList;
import java.util.List;


public class PersonDao {

	
	private SQLiteDatabase db;

	public PersonDao(Context context) {

		PersonOpenHelper helper = new PersonOpenHelper(context);
		db = helper.getWritableDatabase();
	}
	
	

	public boolean add(Persontos ps){
		
		ContentValues values = new ContentValues();
		values.put("title",ps.getTitle_name());
		values.put("url",ps.getUrl());
		values.put("data_time",ps.getDataTime());
		long result = db.insert("person", null, values);
		
		if(result != -1){
			return true;
		}else{
			return false;
		}
		
	}
	
	

	public boolean update(String name,int id){
		

		ContentValues values = new ContentValues();
		values.put("name", name);
		
		int result = db.update("person", values, "id = ?", new String[]{String.valueOf(id)});
		if(result > 0){
			return true;
		}else{
			return false;
		}
		
	}
	

	public boolean delete(String id){
		int result = db.delete("person", "e_id = ?", new String[]{String.valueOf(id)});
		if(result > 0){
			return true;
		}else{
			return false;
		}
	}
	

	public List<Persontos> findAll(){

		Cursor cursor = db.query("person", null, null, null, null, null, null);

		List<Persontos>  ps=new ArrayList<Persontos>();

		while(cursor.moveToNext()){
			  Persontos p=new Persontos();
			String title = cursor.getString(cursor.getColumnIndex("title"));
			String e_id = cursor.getString(cursor.getColumnIndex("e_id"));
			String url = cursor.getString(cursor.getColumnIndex("url"));
			String data_time = cursor.getString(cursor.getColumnIndex("data_time"));
            p.setTitle_name(title);
			p.setDataTime(data_time);
            p.setUrl(url);
			p.setE_id(e_id);
			ps.add(p);
		//	sb.append(""+name+"\t\t = "+phone+"\n");
		 }
		

		closeCursor(cursor);
		
		return ps;
		
	}
	

	public int findId(String name){
		
		Cursor cursor = db.query("person", new String[]{"id"}, "name = ?", new String[]{name}, null, null, null);
		
		int id = -1;
		while(cursor.moveToNext()){
			id = cursor.getInt(cursor.getColumnIndex("id"));
		}
		return id;
	}
	
	
	public void closeCursor(Cursor cursor){
		
		if(cursor != null && !cursor.isClosed()){
			
			cursor.close();
			
		}
		
	}
	
	public void closeDB(){
		
		if(db.isOpen()){
			db.close();
		}
		
	}
	
	
	
	
	

}
