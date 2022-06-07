package com.example.androidproject01.ui.mail;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject01.Mail;
import com.example.androidproject01.MailDBHelper;
import com.example.androidproject01.MyRecyclerViewAdapter;
import com.example.androidproject01.NavMailActivity;
import com.example.androidproject01.R;
import com.example.androidproject01.databinding.FragmentReceiveMailBinding;

import java.util.ArrayList;

public class ReceiveMailFragment extends Fragment {

    private FragmentReceiveMailBinding binding;

    MailDBHelper mailDBHelper;
    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;

    ArrayList<Mail> arrayList;
    Cursor cursor;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NavMailActivity activity = (NavMailActivity)getActivity();
        mailDBHelper = new MailDBHelper(activity);

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_receive_mail, container, false);
        recyclerView = rootView.findViewById(R.id.receiveMailRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        adapter = new MyRecyclerViewAdapter(arrayList, activity);
        recyclerView.setAdapter(adapter);

        //로그인한 사용자의 아이디 가져오기
        SharedPreferences sf = activity.getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        //key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        String id = sf.getString("id","");

        setMailList(id);

        return rootView;
    }

    void setMailList(String id){
        arrayList.clear();
        cursor = mailDBHelper.getMyMailList(id);
        while (cursor.moveToNext()) {
            arrayList.add(new Mail(cursor.getString(cursor.getColumnIndexOrThrow("MAIL_SENDER")),
                    cursor.getString(cursor.getColumnIndexOrThrow("MAIL_RECIPIENT")),
                    cursor.getString(cursor.getColumnIndexOrThrow("MAIL_TITLE")),
                    cursor.getString(cursor.getColumnIndexOrThrow("MAIL_CONTENT")),
                    cursor.getString(cursor.getColumnIndexOrThrow("MAIL_TIME"))));
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        mailDBHelper.close();
        super.onDestroyView();
        binding = null;
    }
}