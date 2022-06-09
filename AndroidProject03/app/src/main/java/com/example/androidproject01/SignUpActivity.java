package com.example.androidproject01;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.androidproject01.databinding.ActivitySignupBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    EditText id, password, name;
    UserDBHelper userDBHelper;

    //추가
    ImageView imageView;
    Spinner spinner;
    PhotoDBHelper photoDBHelper;

    static final int REQUEST_CODE = 1;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userDBHelper = new UserDBHelper(this);
        photoDBHelper = new PhotoDBHelper(this);

        id = (EditText) findViewById(R.id.signup_id); //회원가입창에서 입력받은 id
        password = (EditText) findViewById(R.id.signup_pass); //회원가입창에서 입력받은 password
        name = (EditText) findViewById(R.id.signup_name); //회원가입창에서 입력받은 name
        spinner = (Spinner)findViewById(R.id.major); //회원가입창에서 입력받은 major

        //추가
        imageView = findViewById(R.id.add_photo);
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        //드롭다운버튼 추가//
        //스피너가 들어갈 데이터
        String[] majors = {"멀티미디어공학과","미디어영상"};
        //스피너 객체 생성
        Spinner spinner = findViewById(R.id.major);

        //배열 어뎁터 생성
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, majors);

        //배열 어뎁터 설정
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //설정한 어뎁터 스피너에 셋팅
        spinner.setAdapter(adapter);
    }
    //추가
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_CODE) {
            uri = data.getData();
            InputStream in = null;
            try {
                in = getContentResolver().openInputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            imageView.setImageBitmap(bitmap);
        }
    }
    public void mOnClick(View v) { //가입버튼을 눌렀을 때

        //입력한 값 가져오기
        String i_id = id.getText().toString();
        String i_pwd = password.getText().toString();
        String i_name = name.getText().toString();
        String i_major = spinner.getSelectedItem().toString();

//        //Bitmap -> String
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        img.compress(Bitmap.CompressFormat.PNG, 95, stream); //bitmap compress
//        byte[] arr = stream.toByteArray();
//        String i_img = Base64.encodeToString(arr, Base64.DEFAULT);
//
//        System.out.println("///////////////////////////////////");
//        System.out.println(img);
//        System.out.println(i_img);

        //입력한 값 User객체로 만들기

        User user = new User(i_id, i_pwd, i_name, i_major);

        //DB에 user객체 추가
        userDBHelper.addUser(user);

//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        img.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] data = stream.toByteArray();

        photoDBHelper.addPhoto(uri.toString(), i_id);

        System.out.println("////////////////////////////////////");
        System.out.println("uri : "+ uri.toString());

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void checkOnClick(View v){ //check버튼을 눌렀을 때
        String i_id = id.getText().toString();

        Cursor cursor = userDBHelper.getIdExist(i_id);
        cursor.moveToNext(); //moveToNext()를 호출하여 첫 레코드로 이동
        String count = cursor.getString(cursor.getColumnIndexOrThrow("count")); //alias "count" 읽어오기

        if(count.equals("1")){
            Toast.makeText(this, "이미 존재하는 ID입니다.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }


    @Override
    protected void onDestroy() { //자동으로 userDBHelper 닫기
        userDBHelper.close();
        super.onDestroy();
    }

}