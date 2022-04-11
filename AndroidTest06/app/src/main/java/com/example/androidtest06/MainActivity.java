package com.example.androidtest06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;


public class MainActivity extends AppCompatActivity {
    int mCount = 0;
    TextView mTextCount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextCount=(TextView)findViewById(R.id.count);

        Button decrease, increase;
        decrease=(Button)findViewById(R.id.decrease);
        decrease.setOnLongClickListener(mLongClickListener);
        increase=(Button)findViewById(R.id.increase);
        increase.setOnLongClickListener(mLongClickListener);
    }

    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            removeMessages(0); //타이머 메시지 삭제
            mCount--;

            if (mCount > 0) {
                mTextCount.setText(mCount+"초 후 알람이 울립니다.");
                mHandler.sendEmptyMessageDelayed(0, 1000);
            }
            else if(mCount == 0){
                mTextCount.setText("일어나세요");
                addImg();
            }

        }
    };

    public void addImg() {
        LinearLayout linear = (LinearLayout) findViewById(R.id.linear);
        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.alarm); // 이미지 리소스
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        param.width = 300; // 이미지 너비
        param.height = 300; // 이미지 높이
        param.setMargins(0,50,0,0); // 이미지 마진(왼쪽, 위, 오른쪽, 아래)

        linear.addView(iv, param);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.decrease:
                if(mCount>0)
                    mCount--;
                mTextCount.setText(""+mCount);
                break;
            case R.id.increase:
                mCount++;
                mTextCount.setText(""+mCount);
                break;
            case R.id.timer:
                mHandler.sendEmptyMessage(0);
                break;
        }
    }

    View.OnLongClickListener mLongClickListener = new View.OnLongClickListener() {
        public boolean onLongClick(View v) {
            switch (v.getId()) {
                case R.id.decrease:
                    if((mCount -= 10)>0)
                        mCount -= 10;
                    mTextCount.setText(""+mCount);
                    return true;
                case R.id.increase:
                    mCount += 10;
                    mTextCount.setText(""+mCount);
                    return true;
            }
            return false;
        }
    };
}
