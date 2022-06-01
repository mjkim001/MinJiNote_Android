package com.example.accountmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.accountmanagement.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String userID, userPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textViewId = findViewById(R.id.textViewId);
        TextView textViewPassword = findViewById(R.id.textViewPassword);
        TextView textViewWelcome = findViewById(R.id.textViewWelcome);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPassword = intent.getStringExtra("userPassword");

        textViewId.setText(userID);
        textViewPassword.setText(userPassword);
        textViewWelcome.setText("Welcome, " + userID);

        if(!userID.equals("admin")){
            Button buttonManagement = findViewById(R.id.buttonManagement);
            buttonManagement.setVisibility(View.GONE);
        }
    }

    public void accountManagement(View view){
        new BackgroundTask().execute();

    }
    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;
        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            target="http://gusdml7243.dothome.co.kr/AccountManagement/List.php";
        }
        @Override
        protected void onPostExecute(String s) {
            Intent intent=new Intent(MainActivity.this, ManagementActivity.class);
            intent.putExtra("userList", s);
            startActivity(intent);
        }


    }
}