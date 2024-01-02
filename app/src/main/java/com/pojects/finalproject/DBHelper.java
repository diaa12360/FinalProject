package com.pojects.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String Database_Name = "ChatApp.db";
    private static final int Database_Version = 1;
    private static final String TABLE_NAME = "Messages";
    private static final String Column_UserId = "user_id";
    private static final String Column_Title = "message_title";
    private static final String Column_Body = "message_body";
    private static final String Column_Gender = "user_gender";
    private static  String STATUS = "Success";

    public DBHelper(@Nullable Context context){
        super(context, Database_Name, null, Database_Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                Column_UserId + " INTEGER, " +
                Column_Title + " TEXT, " +
                Column_Body + " TEXT, " +
                Column_Gender + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addMessage(String userId, String gender, String title, String body){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Column_UserId, userId);
        cv.put(Column_Gender, gender);
        cv.put(Column_Title, title);
        cv.put(Column_Body, body);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            STATUS = "FAILED";
        }else{
            STATUS = "SUCCESS";
        }
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        if(db != null){
            return db.rawQuery(query, null);
        }
        return null;
    }

    public String getStatus(){ return STATUS; }
}
