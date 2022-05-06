package com.example.androidproject01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    EditText id;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        id = (EditText) findViewById(R.id.signup_id); //회원가입창에서 입력받은 id
        password = (EditText) findViewById(R.id.signup_pass); //회원가입창에서 입력받은 password

    }

    public void mOnClick(View v) { //가입버튼을 눌렀을 때
        //입력받은 아이디와 비밀번호를 intent로 받아 넘긴다.
        Intent intent= new Intent();
        intent.putExtra("UserInfo",new User(id.getText().toString(),password.getText().toString()));
        setResult(RESULT_OK, intent); //메소드의 결과 저장
        finish(); //activity 종료
    }

    public void checkOnClick(View v){ //check버튼을 눌렀을 때
        Toast.makeText(this, "Good ID", Toast.LENGTH_SHORT).show();
    }
}