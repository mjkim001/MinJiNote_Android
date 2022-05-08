package com.example.androidproject01.ui.slideshow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.androidproject01.NavMailActivity;
import com.example.androidproject01.R;
import com.example.androidproject01.User;
import com.example.androidproject01.UserDBHelper;
import com.example.androidproject01.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    UserDBHelper userDBHelper;

    TextView id;
    EditText pwd;
    EditText name;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NavMailActivity activity = (NavMailActivity)getActivity();
        userDBHelper = new UserDBHelper(activity);

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_slideshow, container, false);
        id = rootView.findViewById(R.id.change_id);
        pwd = rootView.findViewById(R.id.change_pwd);
        name = rootView.findViewById(R.id.change_name);

        //로그인항 아이디 가져오기
        SharedPreferences sf = activity.getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        //key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        String loginID = sf.getString("id","");
        id.setText(loginID);
        //이름 가져오기
        Cursor cursor = userDBHelper.getName(loginID);
        cursor.moveToNext(); //moveToNext()를 호출하여 첫 레코드로 이동
        String s_name = cursor.getString(cursor.getColumnIndexOrThrow("USER_NAME"));
        name.setText(s_name);
        cursor.close();

        Button update = rootView.findViewById(R.id.btn_updateUserInfo);
        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ //사용자 정보 update
                String u_id = id.getText().toString();
                String u_pwd =  pwd.getText().toString();
                String u_name =  name.getText().toString();

                //update할 사용자 정보 user객체로 만든다.
                User user = new User(u_id, u_pwd, u_name);

                //DB에 user객체를 update
                userDBHelper.updateUserInfo(user);

                Toast.makeText(activity, "사용자 정보를 변경하였습니다.", Toast.LENGTH_SHORT).show();

                //정보 수정후 다시 메인 페이지로 돌아간다.
                Intent intent = new Intent(activity, NavMailActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        userDBHelper.close();
        super.onDestroyView();
        binding = null;
    }
}