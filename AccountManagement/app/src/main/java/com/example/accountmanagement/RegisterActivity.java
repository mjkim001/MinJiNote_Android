package com.example.accountmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextId, editTextPassword, editTextName, editTextAge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextId = findViewById(R.id.editTextId);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
    }

    public void onClick(View view) {
        String userID = editTextId.getText().toString();
        String userPassword = editTextPassword.getText().toString();
        String userName = editTextName.getText().toString();
        int userAge = Integer.parseInt(editTextAge.getText().toString());
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { //HTTP POST 요청 결과가 도착하면 호출
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean isSuccess = jsonResponse.getBoolean("success");
                    if (isSuccess) {
                        //Dialog 생성
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("Register Success.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Dialog 창 닫기
                                dialog.dismiss();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        });
                        //Dialog를 보여줌
                        builder.create().show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        // listener를 등록하지 않으면 클릭시 dialog가 닫히기만 함
                        builder.setMessage("Register Failed.").setNegativeButton("Retry", null).create().show();
                        //positive와 negative의 기능적차이는 없으며 동시에 존재할 경우 그 위치에만 상관있다 <Negative><Positive>
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //HTTP 요청 메시지를 생성
        RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userAge, responseListener);
        //RequestQueue 생성
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        //HTTP 요청 메시지를 보냄
        requestQueue.add(registerRequest);
    }
}