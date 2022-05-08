package com.example.androidproject01;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    EditText id, password, name;

    UserDBHelper userDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userDBHelper = UserDBHelper.getInstance(this);

        id = (EditText) findViewById(R.id.signup_id); //회원가입창에서 입력받은 id
        password = (EditText) findViewById(R.id.signup_pass); //회원가입창에서 입력받은 password
        name = (EditText) findViewById(R.id.signup_name); //회원가입창에서 입력받은 name
    }

    public void mOnClick(View v) { //가입버튼을 눌렀을 때
        //입력한 값 가져오기
        String i_id = id.getText().toString();
        String i_pwd = password.getText().toString();
        String i_name = name.getText().toString();

        //입력한 값 User객체로 만들기
        User user = new User(i_id, i_pwd, i_name);

        //DB에 user객체 추가
        userDBHelper.addUser(user);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void checkOnClick(View v){ //check버튼을 눌렀을 때
        String i_id = id.getText().toString();

        Cursor cursor = userDBHelper.getIdExist(i_id);
        cursor.moveToNext(); //moveToNext()를 호출하여 첫 레코드로 이동
        String count = cursor.getString(cursor.getColumnIndexOrThrow("count")); //alias "count" 읽어오기

        if(count.equals("1")){
            Toast.makeText(this, "이미 존재하는 ID입니다.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }


    @Override
    protected void onDestroy() { //자동으로 userDBHelper 닫기
        userDBHelper.close();
        super.onDestroy();
    }

}