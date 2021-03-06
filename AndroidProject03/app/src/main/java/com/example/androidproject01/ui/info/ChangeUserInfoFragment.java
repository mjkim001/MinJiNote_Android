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

        //???????????? ????????? ????????????
        SharedPreferences sf = activity.getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        //key??? ????????? ?????? ????????? ??????. ???????????? ???????????? ????????? ""??? ??????
        String loginID = sf.getString("id","");
        id.setText(loginID);

        //???????????? ????????????
        Cursor cursor_pwd = userDBHelper.getPwd(loginID);
        cursor_pwd.moveToNext(); //moveToNext()??? ???????????? ??? ???????????? ??????
        String s_pwd = cursor_pwd.getString(cursor_pwd.getColumnIndexOrThrow("USER_PWD"));
        pwd.setText(s_pwd);
        cursor_pwd.close();

        //?????? ????????????
        Cursor cursor_name = userDBHelper.getName(loginID);
        cursor_name.moveToNext(); //moveToNext()??? ???????????? ??? ???????????? ??????
        String s_name = cursor_name.getString(cursor_name.getColumnIndexOrThrow("USER_NAME"));
        name.setText(s_name);
        cursor_name.close();

        //??????
        //?????? ????????????
        Cursor cursor_major = userDBHelper.getMajor(loginID);
        cursor_major.moveToNext(); //moveToNext()??? ???????????? ??? ???????????? ??????
        String s_major = cursor_major.getString(cursor_major.getColumnIndexOrThrow("USER_MAJOR"));
        cursor_major.close();

        //?????????????????? ??????//
        //???????????? ????????? ?????????
        String[] majors;
        if(s_major.equals("????????????????????????")){
            majors = new String[]{"????????????????????????", "???????????????"};
        }
        else {
            majors = new String[]{"???????????????", "????????????????????????"};
        }

        //?????? ????????? ??????
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, majors);

        //?????? ????????? ??????
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //????????? ????????? ???????????? ??????
        spinner.setAdapter(adapter);

        //db?????? ?????? uri????????????
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

        Uri pre_uri = uri; //?????? uri

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
            public void onClick(View view){ //????????? ?????? update
                String u_id = id.getText().toString();
                String u_pwd =  pwd.getText().toString();
                String u_name =  name.getText().toString();
                String u_major = spinner.getSelectedItem().toString();

                //update??? ????????? ?????? user????????? ?????????.
                User user = new User(u_id, u_pwd, u_name, u_major);

                //DB??? user????????? update
                userDBHelper.updateUserInfo(user);
                Uri cur_uri = uri; //?????? uri
                if(pre_uri != cur_uri){
                    photoDBHelper.updatePhoto(uri.toString(), u_id);
                }


                Toast.makeText(activity, "????????? ????????? ?????????????????????.", Toast.LENGTH_SHORT).show();

                //?????? ????????? ?????? ?????? ???????????? ????????????.
                Intent intent = new Intent(activity, NavMailActivity.class);
                startActivity(intent);
            }
        });





        return rootView;
    }
    //??????
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