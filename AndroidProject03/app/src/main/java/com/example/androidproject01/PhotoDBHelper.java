package com.example.androidproject01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;

public class PhotoDBHelper extends SQLiteOpenHelper {
    SQLiteDatabase writableDB, readableDB;
    public PhotoDBHelper(Context context) {
        super(context, "Photo.db", null, 1);
        readableDB = getReadableDatabase();
        writableDB = getWritableDatabase();
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Photo ( _id INTEGER PRIMARY KEY AUTOINCREMENT, USERID TEXT, URI TEXT);");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Photo");
        onCreate(db);
    }

    public void addPhoto(String uri, String userid) {
        ContentValues values = new ContentValues();
        values.put("USERID", userid);
        values.put("URI", uri);
        writableDB.insert("Photo", null, values);

    }
    public Cursor getCursorForPhoto(String userid) {

        System.out.println("selectQuery : "+"SELECT URI FROM Photo WHERE USERID = '" + userid + "'");
        String selectQuery = "SELECT URI FROM Photo WHERE USERID = '" + userid + "'";
        return readableDB.rawQuery(selectQuery, null);
    }

    public void updatePhoto(String uri, String userid){
        writableDB.execSQL(
                "UPDATE Photo " +
                        "SET URI = '" + uri +"'" +
                        "WHERE USERID = '" + userid + "'");
    }
    
    
}

