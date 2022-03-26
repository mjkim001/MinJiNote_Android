package com.example.androidproject01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MailActivity extends AppCompatActivity {
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");


        TextView t1 = (TextView) findViewById(R.id.mail_id1);
        t1.setText(id);

        TextView t2 = (TextView) findViewById(R.id.mail_id2);
        t2.setText(id);

        TextView t3 = (TextView) findViewById(R.id.mail_id3);
        t3.setText(id);
    }

}
