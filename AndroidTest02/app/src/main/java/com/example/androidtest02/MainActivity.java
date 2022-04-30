package com.example.androidtest02;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main2);
    }

    /*
    //버튼을 누르면 버튼 밑으로 이미지가 생기고 버튼을 계속 누르면 이미지가 계속 쌓여서 스크롤을 통해 볼 수 있다.
    public void mOnClick(View v) {
        LinearLayout li = (LinearLayout)View.inflate(
                this, R.layout.newimages, null);
        LinearLayout linear = (LinearLayout)findViewById(R.id.Mainlinear);
        linear.addView(li);
    }
    */
    public void mOnClick(View v) {
        RelativeLayout rel = (RelativeLayout)View.inflate(
                this, R.layout.newmessage, null);
        LinearLayout linear = (LinearLayout)findViewById(R.id.linear);
        ImageView imageView = (ImageView)rel.findViewById(R.id.picture1);

        if (linear.getChildCount() % 2 == 0) {
            rel.setBackgroundColor(Color.GRAY);
            imageView.setImageResource(R.drawable.image2);

        } else {
            rel.setBackgroundColor(Color.RED);
            imageView.setImageResource(R.drawable.image);
        }
        linear.addView(rel);
    }


}