package com.example.androidtest07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayList<String> temp;
    ArrayAdapter<String> adapter;
    ListView list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items = new ArrayList<String>();
        temp = new ArrayList<String>();

        items.add("First");
        items.add("Second");
        items.add("Third");
        items.add("Fourth");
        items.add("Fifth");

        temp.addAll(items); //backup용

        adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_multiple_choice, items);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
    public void mOnClick(View v) {
        EditText ed = (EditText) findViewById(R.id.newitem);
        EditText ed2 = (EditText) findViewById(R.id.searchitem);
        EditText search = (EditText) findViewById(R.id.searchitem);
        switch (v.getId()) {
            case R.id.add:
                String text = ed.getText().toString();
                if (text.length() != 0) {
                    items.add(text);
                    temp.add(text);
                    ed.setText("");
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.delete:
                SparseBooleanArray sb = list.getCheckedItemPositions();
                if (sb.size() != 0) {
                    for (int i = list.getCount() - 1; i >= 0; i--) {
                        if (sb.get(i)) {
                            items.remove(i);
                            temp.remove(i);
                        }
                    }
                    list.clearChoices();
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.search:
                String text2 = ed2.getText().toString();

                items.clear();

                if(text2 == "") {
                    items.addAll(temp);
                }
                else {
                    for(int i = 0; i < temp.size(); i++) {
                        if(temp.get(i).toUpperCase().contains(text2.toUpperCase())) { //대소문자 구분없이 검색가능
                            //검색한 문자를 대문자로 만들어 검색한다.
                            items.add(temp.get(i));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
        }
    }
}