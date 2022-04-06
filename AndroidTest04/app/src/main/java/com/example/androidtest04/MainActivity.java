                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  package com.example.androidtest04;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private int score = 0;

    public int getScore(){
        return score;
    }

    public void plusScore(){
        score++;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        changeToMainFragment();
    }
    public void changeToMainFragment() {
        score = 0;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new MainFragment()).commit();
    }

    public void changeToSubFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer,new SubFragment()).commit();
    }

    public void changeToResFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer,new ResFragment()).commit();
    }
}