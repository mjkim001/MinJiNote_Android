package com.example.androidproject01.ui.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.androidproject01.MailDBHelper;
import com.example.androidproject01.NavMailActivity;
import com.example.androidproject01.R;
import com.example.androidproject01.databinding.FragmentGalleryBinding;


public class DetailFragment extends Fragment {

    private FragmentGalleryBinding binding;

    MailDBHelper mailDBHelper;

    TextView mailSender, mailRecipient, mailTitle, mailTime,mailContent;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NavMailActivity activity = (NavMailActivity)getActivity();
        mailDBHelper = new MailDBHelper(activity);

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_maildetail, container, false);

        mailSender = rootView.findViewById(R.id.mailSender);
        mailRecipient = rootView.findViewById(R.id.mailRecipient);
        mailTitle = rootView.findViewById(R.id.mailTitle);
        mailTime = rootView.findViewById(R.id.mailTime);
        mailContent = rootView.findViewById(R.id.mailContent);

        //로그인한 사용자의 아이디 가져오기
        SharedPreferences sf = activity.getSharedPreferences("mailDetail", Context.MODE_PRIVATE);
        //key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        String mail_sender = sf.getString("mail_sender","");
        String mail_recipient = sf.getString("mail_recipient","");
        String mail_title = sf.getString("mail_title","");
        String mail_content = sf.getString("mail_content","");
        String mail_time = sf.getString("mail_time","");

        mailSender.setText(mail_sender);
        mailRecipient.setText(mail_recipient);
        mailTitle.setText(mail_title);
        mailTime.setText(mail_time);
        mailContent.setText(mail_content);



        return rootView;
    }

    @Override
    public void onDestroyView() {
        mailDBHelper.close();
        super.onDestroyView();
        binding = null;
    }
}