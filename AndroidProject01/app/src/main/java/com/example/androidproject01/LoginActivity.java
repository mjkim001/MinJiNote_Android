package com.example.androidproject01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    TextView id; //회원가입 창에서 입력받은 id저장
    TextView pass; //회원가입 창에서 입력받은 pass저장

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //setContentView(R.layout.activity_signup);
        //setContentView(R.layout.activity_mail);

        //입력한 id와 pass를 가져온다.
        id = (TextView) findViewById(R.id.edit_id);
        pass = (TextView) findViewById(R.id.edit_pass);
    }

    public void mOnClick(View v) {
        switch (v.getId()){
            case R.id.login:


                //회원가입 창에서 입력한 id와 password가 일치하는 지 확인
                //일치하면 메일 창으로 이동



                //일치하지 않으면 "잘못입력하였습니다." 메시지 출력
                if(true){ //아이디 & 비밀번호가 일치할 경우

                }else{ //아이디 & 비밀번호가 일치하지 않을 경우
                    String str = "잘못입력하였습니다";
                    Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.signup:
                //signup페이지로 이동
                Intent intent = new Intent(this, SignUpActivity.class);
                //회워가입 창에서 입력받은 내용을 가져와야하기 때문에 startActivityForResult()사용
                startActivityForResult(intent,0);
                break;
        }
    }
}