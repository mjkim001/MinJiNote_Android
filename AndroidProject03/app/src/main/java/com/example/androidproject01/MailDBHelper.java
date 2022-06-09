package com.example.androidproject01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MailDBHelper extends SQLiteOpenHelper {
    SQLiteDatabase writableDB, readableDB;

    //singleton

    // Context는 현재 실행중인 Activity를 의미(Activity가 Context에 상속되었기 때문에 this시 해당 액티비티 리턴)
    public MailDBHelper(Context context) {
        // 데이터 베이스 만들기(Mail.db로 만듬)
        super(context,"Mail.db",null,1);
        // 쓰기 전용 DB 객체
        writableDB = getWritableDatabase();
        // 읽기 전용 DB 객체
        readableDB = getReadableDatabase();
    }

    //DB 만들때 자동으로 호출됨(테이블 생성)
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE Mail ( _index INTEGER PRIMARY KEY AUTOINCREMENT, MAIL_SENDER TEXT , MAIL_RECIPIENT TEXT, MAIL_TITLE TEXT, MAIL_CONTENT TEXT, MAIL_TIME TEXT);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS Mail");
        onCreate(db);
    }

    // User 클래스의 객체에 담긴 내용을 받아와서 해당 테이블에 내용을 넣어줌
    public void insertMail(Mail mail) {
        ContentValues values = new ContentValues();
        values.put("MAIL_SENDER", mail.getSender());
        values.put("MAIL_RECIPIENT", mail.getRecipient());
        values.put("MAIL_TITLE", mail.getTitle());
        values.put("MAIL_CONTENT", mail.getContent());
        values.put("MAIL_TIME", mail.getTime());
        writableDB.insert("Mail", null, values);
    }

    // 로그인한 사용자가 받은 메일 가져오기
    public Cursor getMyMailList(String id) {
        String selectQuery = "SELECT * FROM Mail WHERE MAIL_RECIPIENT = '" + id + "'";

        return readableDB.rawQuery(selectQuery, null);
    }

    // 로그인한 사용자가 보낸 메일 가져오기
    public Cursor getMySendMailList(String id) {
        String selectQuery = "SELECT * FROM Mail WHERE MAIL_SENDER = '" + id + "'";

        return readableDB.rawQuery(selectQuery, null);
    }
}