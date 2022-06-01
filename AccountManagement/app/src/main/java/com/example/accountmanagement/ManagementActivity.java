package com.example.accountmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManagementActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserItemAdapter adapter;
    private List<User> saveddUserList, searchedUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        Intent intent = getIntent();
        saveddUserList = new ArrayList<>();
        searchedUserList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            String userID, userPassword, userName, userAge;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject row = jsonArray.getJSONObject(i);
                userID = row.getString("userID");
                userPassword = row.getString("userPassword");
                userName = row.getString("userName");
                userAge = row.getString("userAge");
                User user = new User(userID, userPassword, userName, userAge);
                if(!userID.equals("admin")){
                    saveddUserList.add(user);
                    searchedUserList.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter = new UserItemAdapter(getApplicationContext(), saveddUserList,searchedUserList, this);
        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        EditText editTextSearch = findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchedUser(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    public void searchedUser(String search) {
        searchedUserList.clear();
        for (int i = 0; i < saveddUserList.size(); i++) {
            if (saveddUserList.get(i).getUserID().contains(search)) {
                searchedUserList.add(saveddUserList.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }
}