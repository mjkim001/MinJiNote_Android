package com.example.androidproject01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDBHelper extends SQLiteOpenHelper {
    SQLiteDatabase writableDB, readableDB;

    private static UserDBHelper userDBHelper;

    //singleton

    // Context는 현재 실행중인 Activity를 의미(Activity가 Context에 상속되었기 때문에 this시 해당 액티비티 리턴)
    public UserDBHelper(Context context) {
        // 데이터 베이스 만들기(User.db로 만듬)
        super(context,"User.db",null,1);
        // 쓰기 전용 DB 객체
        writableDB = getWritableDatabase();
        // 읽기 전용 DB 객체
        readableDB = getReadableDatabase();
    }

    //DB 만들때 자동으로 호출됨(테이블 생성)
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE User (USER_ID TEXT PRIMARY KEY, USER_PWD TEXT, USER_NAME TEXT);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }

    // User 클래스의 객체에 담긴 내용을 받아와서 해당 테이블에 내용을 넣어줌
    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put("USER_ID", user.getId());
        values.put("USER_PWD", user.getPass());
        values.put("USER_NAME", user.getName());
        writableDB.insert("User", null, values);
    }

    public void updateUserInfo(User user){
        writableDB.execSQL(
                "UPDATE User " +
                   "SET USER_PWD = '" + user.getPass() +"'," +
                       "USER_NAME = '"+ user.getName() +"'" +
                 "WHERE USER_ID = '" + user.getId() + "'");
    }



    // Cursor를 사용해 select를 사용해 받아온 값을 리턴해줌
    // 아이디 비밀번호 일치 확인
    public Cursor getUserExist(String id, String pwd) {
        String selectQuery = "SELECT count(*) as count FROM User WHERE USER_ID = '" + id +"' AND USER_PWD = '" + pwd + "'";
        return readableDB.rawQuery(selectQuery, null);
    }

    // 아이디 유효성 검사
    public Cursor getIdExist(String id) {
        String selectQuery = "SELECT count(*) as count FROM User WHERE USER_ID = '" + id + "'";

        return readableDB.rawQuery(selectQuery, null);
    }

    // 로그인한 계정 이름 가져오기
    public Cursor getName(String id) {
        String selectQuery = "SELECT USER_NAME FROM User WHERE USER_ID = '" + id + "'";

        return readableDB.rawQuery(selectQuery, null);
    }

}
