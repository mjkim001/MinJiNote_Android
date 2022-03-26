package com.example.androidproject01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> activityResultLauncher;

    TextView loginId; //로그인 창에서 입력받은 id저장
    TextView loginPass; //로그인 창에서 입력받은 pass저장

    String intentId; //회원가입 창에서 입력받은 id저장
    String intentPass; //회원가입 창에서 입력받은 pass저장

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //로그인 창에서 입력한 id와 pass를 가져온다.
        loginId = (TextView) findViewById(R.id.login_id);
        loginPass = (TextView) findViewById(R.id.login_pass);

        activityResultLauncher = registerForActivityResult(new
                ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) { //회원가입을 완료했을경우
                User user = (User) result.getData().getSerializableExtra("UserInfo");
                intentId = user.getId();
                intentPass = user.getPass();
            }
        });

    }

    public void mOnClick(View v) {
        switch (v.getId()){
            case R.id.login:
                //회원가입 창에서 입력한 id와 password가 일치하는 지 확인
                //일치하면 메일 창으로 이동
                //로그인 창에서 입력받은 아이디, 비밀번호 가져오기
                String id = loginId.getText().toString();
                String pass = loginPass.getText().toString();

                if(id.equals(intentId) && pass.equals(intentPass)){ //아이디 & 비밀번호가 일치할 경우
                    Intent intent = new Intent(this, MailActivity.class);

                    intent.putExtra("ID", id);
                    startActivity(intent);

                }else{ //아이디 & 비밀번호가 일치하지 않을 경우
                    //일치하지 않으면 "잘못입력하였습니다." 메시지 출력
                    String str = "잘못입력하였습니다";
                    Toast.makeText(this, str+id+intentId, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.signup:
                //signup페이지로 이동
                Intent intent = new Intent(this, SignUpActivity.class);
                activityResultLauncher.launch(intent);
                break;
        }
    }
}