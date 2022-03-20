package com.example.androidtest01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_main2);
    }

    public void mOnClick(View v) {
        EditText edit1 = (EditText) findViewById(R.id.edit1);
        EditText edit2 = (EditText) findViewById(R.id.edit2);
        String str = "ID : " + edit1.getText().toString() + "\n Password : " +edit2.getText().toString();
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
    }

}