package com.ivali.myapplicationapp.dbmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zjl on 15-3-11.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final  String  DATABASE_NAME="test.db";
    private static final  int  DATABASE_VERSION=1;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS person(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         " name VARCHAR,age INTEGER, info TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("ALTER TABLE person COLUMN other STRING");
    }
}
