package com.example.androidtest03;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {
    String text;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        //키값이 "TextIn"인 intent를 불러와서 text애 저장
        Intent intent = getIntent();
        text = intent.getStringExtra("TextIn");

        //버튼의 text수정
        Button b = (Button) findViewById(R.id.btnnewmessage);
        b.setText(text + "나와라");
    }
    public void mOnClick(View v) {
        //activity_sub.xml 에 레이아웃을 추가하기 위한
        //activity_sub.xml 의 LinearLayout 을 가리키는 변수
        LinearLayout linear = (LinearLayout)findViewById(R.id.linear);

        //버튼을 누룰때 추가될 레이아웃 newmessage.xml의
        //RelativeLayout을 가리키는 변수
        RelativeLayout rel = (RelativeLayout)View.inflate(
                this, R.layout.newmessage, null);

        ImageView imageView = (ImageView)rel.findViewById(R.id.picture1); //변경할 newmessage.xml의 이미지
        TextView textView = (TextView) rel.findViewById(R.id.content); //변경할 newmessage.xml의 텍스트

        if (linear.getChildCount() % 2 == 0) {
            rel.setBackgroundColor(Color.GRAY);
            imageView.setImageResource(R.drawable.image2);
            textView.setText(text+"2");
        } else {
            rel.setBackgroundColor(Color.RED);
            imageView.setImageResource(R.drawable.image);
            textView.setText(text+"1");
        }

        linear.addView(rel); //변경된 newmessage.xml을 activity_sub.xml에 추가
    }
}