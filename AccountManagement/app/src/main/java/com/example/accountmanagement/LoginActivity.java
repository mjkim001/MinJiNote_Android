package com.example.accountmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText editTextID, editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextID = findViewById(R.id.editTextId);
        editTextPassword = findViewById(R.id.editTextPassword);
    }
    public void onClick(View v) {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }
    public void onClickLogin(View v) { //login 버튼 클릭 시 호출
        final String userID = editTextID.getText().toString();
        final String userPassword = editTextPassword.getText().toString();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean isSuccess = jsonResponse.getBoolean("success");
                    if (isSuccess) { //JSON으로 넘겨 받은 부가 데이터 조회
                        String userID = jsonResponse.getString("userID");
                        String userPassword = jsonResponse.getString("userPassword");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("userID", userID);
                        intent.putExtra("userPassword", userPassword);
                        startActivity(intent); //intent에 부가 데이터 저장
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Login Failed.").setNegativeButton("Retry", null).create().show();}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(loginRequest);
    }
}
