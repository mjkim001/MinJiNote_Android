package com.example.accountmanagement;


import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static private String URL = "http://minjikim.dothome.co.kr/AccountManagement/Register.php";
    private Map<String, String> parameters;
    public RegisterRequest(String userID, String userPassword, String userName, int userAge, Response.Listener<String> listener) {

        super(Method.POST, URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RegisterRequest", error.getMessage());
            }
        });

        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
        parameters.put("userAge", userAge + "");
    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
