package com.example.loms;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView WelcomText;
    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authManager = new AuthManager();

        //TextView 연결
        WelcomText = findViewById(R.id.WelcomText);

        //Intent에서 email수신
        Intent intent = getIntent();
        String email = intent.getStringExtra("user_email");

        //email에서 이름 추출 및 메시지 설정
        if( email != null ) {
            String username = email.split("@")[0];
            WelcomText.setText(username+"님의 목표!");
        }

        // 버튼 선언
        Button goalListButton = findViewById(R.id.goalListButton);
        Button logoutButton = findViewById(R.id.logoutButton);
        Button SettingButton = findViewById(R.id.SettingButton);

        // 목표 리스트 버튼
        goalListButton.setOnClickListener(v -> {
            Intent goalIntent = new Intent(MainActivity.this, GoalListActivity.class);
            goalIntent.putExtra("user_email",email);
            startActivity(goalIntent);
        });

        // 로그 아웃 버튼
        logoutButton.setOnClickListener(v->{
            Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(logoutIntent);
            finish(); //뒤로 돌아가기 버튼으로 돌아가지않도록 설정
        });

        // 환경설정 버튼
        SettingButton.setOnClickListener(v->{
            Intent settingIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingIntent);
        });
    }
}
