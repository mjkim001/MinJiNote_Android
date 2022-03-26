package com.example.androidtest03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {
    TextView id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = (TextView) findViewById(R.id.edit_id); //입력받은 id 저장
    }
    public void mOnClick(View v) {
        Intent intent = new Intent(this, SubActivity.class);
        intent.putExtra("TextIn", id.getText().toString());
        //입력받은 id를 "TextIn"키값으로 저장해서 intent에 넣어준다.
        startActivity(intent);
        //다시 돌려받을 값이 없기 때문에 startActivity() 사용
    }
}