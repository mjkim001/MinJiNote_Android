package com.example.androidproject01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

    }

    public void mOnClick(View v) { //가입버튼을 눌렀을 때
        //입력받은 아이디와 비밀번호를 intent로 받아 넘긴다.
        EditText edit_id = (EditText) findViewById(R.id.edit_id);
        EditText edit_password = (EditText) findViewById(R.id.edit_pass);
        String str = "ID : " + edit_id.getText().toString() + "\n Password : " + edit_password.getText().toString();
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void checkOnClick(View v){ //check버튼을 눌렀을 때
        Toast.makeText(this, "Good ID", Toast.LENGTH_SHORT).show();
    }
}
