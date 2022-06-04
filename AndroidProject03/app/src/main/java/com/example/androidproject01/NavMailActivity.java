package com.example.androidproject01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidproject01.databinding.ActivityNavMailBinding;

public class NavMailActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavMailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fragment_gallery에 대한 텍스트 뷰에 프로그램으로 접근
        binding = ActivityNavMailBinding.inflate(getLayoutInflater());
        // activity_main로 뷰를 생성, 정의된 속성 사용
        setContentView(binding.getRoot());

        // 기본 ActionBar을 xml에서 만든 ToolBar로 교체
        setSupportActionBar(binding.appBarNavMail.toolbar);

        // 네비게이션 버튼 호출 및 누를 때 분류가 나타남
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_nav_mail);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //추가
        //galleryFragment에서 DetailFragment로 이동할때
        Intent intent = getIntent();

        if(intent.getStringExtra("mail_sender") != null){
            String mail_sender = intent.getStringExtra("mail_sender");
            String mail_recipient = intent.getStringExtra("mail_recipient");
            String mail_title = intent.getStringExtra("mail_title");
            String mail_content = intent.getStringExtra("mail_content");
            String mail_time = intent.getStringExtra("mail_time");

            SharedPreferences sharedPreferences = getSharedPreferences("mailDetail",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // 로그인한 사용자의 아이디, key, value형태로 저장
            editor.putString("mail_sender",mail_sender);
            editor.putString("mail_recipient",mail_recipient);
            editor.putString("mail_title",mail_title);
            editor.putString("mail_content",mail_content);
            editor.putString("mail_time",mail_time);

            editor.commit();

            navController.navigate(R.id.action_home_to_detail);
        }



    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_nav_mail);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}