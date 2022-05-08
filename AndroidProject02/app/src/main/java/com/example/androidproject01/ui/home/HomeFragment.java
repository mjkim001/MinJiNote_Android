package com.example.androidproject01.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.androidproject01.Mail;
import com.example.androidproject01.MailDBHelper;
import com.example.androidproject01.NavMailActivity;
import com.example.androidproject01.R;
import com.example.androidproject01.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    EditText recipient, title, content;

    MailDBHelper mailDBHelper;
    Date sDate;
    SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        NavMailActivity activity = (NavMailActivity)getActivity();
        mailDBHelper = new MailDBHelper(activity);

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        recipient = (EditText) rootView.findViewById(R.id.mail_recipient);
        title = (EditText) rootView.findViewById(R.id.mail_title);
        content = (EditText) rootView.findViewById(R.id.mail_content);

        Button reset = rootView.findViewById(R.id.btn_textReset);
        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ //취소버튼을 눌렀을 경우 입력한 내용을 초기화한다.
                recipient.setText("");
                title.setText("");
                content.setText("");
            }
        });

        Button send = rootView.findViewById(R.id.btn_sendMessage);
        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ //보내기 버튼을 눌렀을 경우
                //sender 가져오기
                //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
                SharedPreferences sf = activity.getSharedPreferences("loginUser",Context.MODE_PRIVATE);
                //key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
                String sender = sf.getString("id","");

                String rec = recipient.getText().toString();
                String tit = title.getText().toString();
                String con = content.getText().toString();


                long now = System.currentTimeMillis();
                sDate = new Date(now);
                String date = dFormat.format(sDate);

                System.out.println("date : " + date);

                //입력한 값 Mail객체 만들기
                Mail mail = new Mail(sender, rec, tit, con, date);

                //DB에 Mail객체 추가
                mailDBHelper.insertMail(mail);

                Toast.makeText(activity, "메일을 보냈습니다.", Toast.LENGTH_SHORT).show();

                //메일을 보낸후 다시 메인 페이지로 돌아간다.
                Intent intent = new Intent(activity, NavMailActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        mailDBHelper.close();
        super.onDestroyView();
        binding = null;
    }

}