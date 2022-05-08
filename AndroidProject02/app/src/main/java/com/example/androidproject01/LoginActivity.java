package com.example.androidproject01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> activityResultLauncher;
    User user;
    TextView loginId; //로그인 창에서 입력받은 id저장
    TextView loginPass; //로그인 창에서 입력받은 pass저장
    CheckBox saveLogin;


    UserDBHelper userDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //로그인 창에서 입력한 id와 pass, 로그인 저장여부를 가져온다.
        loginId = (TextView) findViewById(R.id.login_id);
        loginPass = (TextView) findViewById(R.id.login_pass);
        saveLogin = (CheckBox) findViewById(R.id.saveLogin);

        userDBHelper = UserDBHelper.getInstance(this);

        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        SharedPreferences sf = getSharedPreferences("saveLogin",MODE_PRIVATE);

        //key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        String id = sf.getString("id","");
        String pwd = sf.getString("pwd","");

        loginId.setText(id);
        loginPass.setText(pwd);

    }

    public void mOnClick(View v) {
        //로그인 창에서 입력받은 아이디, 비밀번호 가져오기
        String id = loginId.getText().toString();
        String pwd = loginPass.getText().toString();

        if(saveLogin.isChecked()){ //로그인 기억하기 버튼을 체크했을 경우 (입력한 아이디 비밀번호 저장)
            //SharedPreferences를 sFile이름, 기본모드로 설정
            SharedPreferences sharedPreferences = getSharedPreferences("saveLogin",MODE_PRIVATE);
            // 저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // 사용자가 입력한 저장할 데이터 key, value형태로 저장
            editor.putString("id",id);
            editor.putString("pwd",pwd);

            editor.commit();
        }else { //로그인 기억하기 버튼을 체크하지 않았을 경우 (이전에 저장되어있던 아이디 비밀번호 초기화)
            SharedPreferences sharedPreferences = getSharedPreferences("saveLogin",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // 기존에 저장되어 있던 아이디 비밀번호 초기화
            editor.putString("id","");
            editor.putString("pwd","");

            editor.commit();
        }

        switch (v.getId()){
            case R.id.login:
                //회원가입 창에서 입력한 id와 password가 일치하는 지 확인
                //일치하면 메일 창으로 이동

                Cursor cursor = userDBHelper.getUserExist(id, pwd);
                cursor.moveToNext(); //moveToNext()를 호출하여 첫 레코드로 이동
                String count = cursor.getString(cursor.getColumnIndexOrThrow("count")); //alias "count" 읽어오기

                if(count.equals("1")){ //아이디 & 비밀번호가 일치할 경우
                    SharedPreferences sharedPreferences = getSharedPreferences("loginUser",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // 로그인한 사용자의 아이디, key, value형태로 저장
                    editor.putString("id",id);
                    editor.commit();

                    Intent intent = new Intent(this, NavMailActivity.class);
                    startActivity(intent);
                }else { //아이디 & 비밀번호가 일치하지 않을 경우
                    String str = "잘못입력하였습니다";
                    Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                }
                cursor.close();

                break;
            case R.id.signup:
                //signup페이지로 이동
                Intent intent = new Intent(this, SignUpActivity.class);
                activityResultLauncher.launch(intent);
                break;
        }
    }
}