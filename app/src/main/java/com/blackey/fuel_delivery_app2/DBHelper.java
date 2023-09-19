package com.blackey.fuel_delivery_app2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password Text)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }
    public Boolean insertData(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        long result = MyDB.insert("users", null, contentValues);
        if (result==-1)return false;
        else
            return true;
    }

    public  boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor =MyDB.rawQuery("select * from users where username = ?",new String[]{username});
        if (cursor.getCount()>0)

            return true;
        else
            return false;
    }

    public  boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor= MyDB.rawQuery("select * from users where username = ? and password= ?",new  String[]{username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkUsernamePassword(String username, String password) {
        return false;
    }

    public boolean updatePassword(String username, String newPassword) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", newPassword);
        long result = MyDB.update("users", contentValues, "username=?", new String[]{username});
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void savePersonalInfo(String name, String contact) {
    }

    public void deleteAllData() {
    }
}