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
        items.add("First");
        items.add("Second");
        items.add("Third");
        items.add("Fourth");
        items.add("Fifth");
        adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_multiple_choice, items);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
    public void mOnClick(View v) {
        EditText ed = (EditText) findViewById(R.id.newitem);
        EditText ed2 = (EditText) findViewById(R.id.searchitem);
        switch (v.getId()) {
            case R.id.add:
                String text = ed.getText().toString();
                if (text.length() != 0) {
                    items.add(text);
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
                        }
                    }
                    list.clearChoices();
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.search:
                String text2 = ed2.getText().toString();
                //데이터에서 입력받은 문자가 포함된 contain, 리스트만 출력
                //원본 데이터를 저장하는 리스트,
                //결과 출력용 리스트
                //adapter에 갈아끼우는 방식으로
                temp.addAll((Collection<? extends String>) list);
                ((Collection<?>) list).clear();
                for(int i = 0; i < temp.size(); i++){
                    if(temp.contains(text2)){
                        //items.add(i);
                    }
                }
        }
    }
}