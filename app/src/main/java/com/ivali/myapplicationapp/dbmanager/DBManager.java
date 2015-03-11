package com.ivali.myapplicationapp.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjl on 15-3-11.
 */
public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;
    public DBManager(Context context){
        helper=new DBHelper(context);
        db=helper.getWritableDatabase();
    }
    public void add(List<Person> persons){
        db.beginTransaction();
        try{
            for (Person p:persons){
                db.execSQL("INSERT INTO person VALUES(null,?,?,?)",
                        new Object[]{p.getName(),p.getAge(),p.getInfo()});
            }
            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    public void updateAge(Person p){
        ContentValues cv = new ContentValues();
        cv.put("age",p.getAge());
        db.update("person",cv,"name=?",new String[]{p.getName()});
    }

    public void delete(){
        db.execSQL("delete from person where name='tom'");
        db.execSQL("delete from person where name='bill'");
        db.execSQL("delete from person where name='gate'");
        db.execSQL("delete from person where name='joe'");
        db.execSQL("delete from person where name='jhon'");
    }

    public void deleteOldPerson(Person p){
        db.delete("person","age=?",new String[]{String.valueOf(p.getAge())});
    }

    public List<Person> findAllPerson(){
        ArrayList<Person> persons = new ArrayList<Person>();
        Cursor c = db.rawQuery("SELECT * FROM person", null);
        while(c.moveToNext()){
            Person p = new Person();
            p.set_id(c.getInt(c.getColumnIndex("_id")));//自增长的ID
            p.setName(c.getString(c.getColumnIndex("name")));
            p.setAge(c.getInt(c.getColumnIndex("age")));
            p.setInfo(c.getString(c.getColumnIndex("info")));
            persons.add(p);
        }
        c.close();
        return persons;
    }



    public void closeDB(){
        db.close();
    }

}
