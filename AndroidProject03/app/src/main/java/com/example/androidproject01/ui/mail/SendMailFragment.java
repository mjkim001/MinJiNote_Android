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
import com.example.androidproject01.databinding.FragmentSendMailBinding;

import java.util.ArrayList;

public class SendMailFragment extends Fragment {

    private FragmentSendMailBinding binding;

    MailDBHelper mailDBHelper;
    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;

    ArrayList<Mail> arrayList;
    Cursor cursor;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NavMailActivity activity = (NavMailActivity)getActivity();
        mailDBHelper = new MailDBHelper(activity);

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_receive_mail, container, false);
        recyclerView = rootView.findViewById(R.id.sendMailRecyclerView);



        return rootView;
    }

    @Override
    public void onDestroyView() {
        mailDBHelper.close();
        super.onDestroyView();
        binding = null;
    }
}
