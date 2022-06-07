package com.example.androidproject01.ui.info;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.androidproject01.NavMailActivity;
import com.example.androidproject01.PhotoDBHelper;
import com.example.androidproject01.R;
import com.example.androidproject01.User;
import com.example.androidproject01.UserDBHelper;
import com.example.androidproject01.databinding.FragmentChangeUserInfoBinding;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ChangeUserInfoFragment extends Fragment {

    private FragmentChangeUserInfoBinding binding;

    UserDBHelper userDBHelper;
    PhotoDBHelper photoDBHelper;

    TextView id;
    EditText pwd;
    EditText name;

    Spinner spinner;
    Bitmap img;
    ImageView imageView;

    Uri uri;

    static final int REQUEST_CODE = 1;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NavMailActivity activity = (NavMailActivity)getActivity();
        userDBHelper = new UserDBHelper(activity);
        photoDBHelper = new PhotoDBHelper(activity);

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_change_user_info, container, false);
        id = rootView.findViewById(R.id.change_id);
        pwd = rootView.findViewById(R.id.change_pwd);
        name = rootView.findViewById(R.id.change_name);
        spinner = (Spinner)rootView.findViewById(R.id.change_major);
        imageView = rootView.findViewById(R.id.change_photo);

        //로그인한 아이디 가져오기
        SharedPreferences sf = activity.getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        //key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        String loginID = sf.getString("id","");
        id.setText(loginID);

        //이름 가져오기
        Cursor cursor_name = userDBHelper.getName(loginID);
        cursor_name.moveToNext(); //moveToNext()를 호출하여 첫 레코드로 이동
        String s_name = cursor_name.getString(cursor_name.getColumnIndexOrThrow("USER_NAME"));
        name.setText(s_name);
        cursor_name.close();

        //추가
        //학과 가져오기
        Cursor cursor_major = userDBHelper.getMajor(loginID);
        cursor_major.moveToNext(); //moveToNext()를 호출하여 첫 레코드로 이동
        String s_major = cursor_major.getString(cursor_major.getColumnIndexOrThrow("USER_MAJOR"));
        cursor_major.close();

        //드롭다운버튼 추가//
        //스피너가 들어갈 데이터
        String[] majors;
        if(s_major.equals("멀티미디어공학과")){
            majors = new String[]{"멀티미디어공학과", "미디어영상"};
        }
        else {
            majors = new String[]{"미디어영상", "멀티미디어공학과"};
        }

        //배열 어뎁터 생성
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, majors);

        //배열 어뎁터 설정
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //설정한 어뎁터 스피너에 셋팅
        spinner.setAdapter(adapter);

        //db에서 사진 uri가져오기
        System.out.println("////////////////////////////");
        System.out.println("loginID : "+loginID);

        Cursor cursor_img = photoDBHelper.getCursorForPhoto(loginID);
        cursor_img.moveToNext();
        String u_img = cursor_img.getString(cursor_img.getColumnIndexOrThrow("URI"));
        System.out.println("////////////////////////////");
        System.out.println("u_img : " + u_img);
        cursor_img.close();

        uri = Uri.parse(u_img);
        imageView.setImageURI(uri);

        Uri pre_uri = uri; //처음 uri

        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        Button update = rootView.findViewById(R.id.btn_updateUserInfo);
        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ //사용자 정보 update
                String u_id = id.getText().toString();
                String u_pwd =  pwd.getText().toString();
                String u_name =  name.getText().toString();
                String u_major = spinner.getSelectedItem().toString();

                //update할 사용자 정보 user객체로 만든다.
                User user = new User(u_id, u_pwd, u_name, u_major);

                //DB에 user객체를 update
                userDBHelper.updateUserInfo(user);
                Uri cur_uri = uri; //현재 uri
                if(pre_uri != cur_uri){
                    photoDBHelper.updatePhoto(uri.toString(), u_id);
                }


                Toast.makeText(activity, "사용자 정보를 변경하였습니다.", Toast.LENGTH_SHORT).show();

                //정보 수정후 다시 메인 페이지로 돌아간다.
                Intent intent = new Intent(activity, NavMailActivity.class);
                startActivity(intent);
            }
        });





        return rootView;
    }
    //추가
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_CODE) {
            uri = data.getData();
            InputStream in = null;
            try {
                in = getActivity().getContentResolver().openInputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            imageView.setImageBitmap(bitmap);
        }
    }
    @Override
    public void onDestroyView() {
        userDBHelper.close();
        super.onDestroyView();
        binding = null;
    }
}