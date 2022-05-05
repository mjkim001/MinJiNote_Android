package com.example.accountmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textViewId = findViewById(R.id.textViewId);
        TextView textViewPassword = findViewById(R.id.textViewPassword);
        TextView textViewWelcome = findViewById(R.id.textViewWelcome);
        Intent intent = getIntent(); //intent로 넘어온 부가 데이터 조회
        String userID = intent.getStringExtra("userID");
        String userPassword = intent.getStringExtra("userPassword");
        textViewId.setText(userID);
        textViewPassword.setText(userPassword);
        textViewWelcome.setText("Welcome, " + userID);
    }
}