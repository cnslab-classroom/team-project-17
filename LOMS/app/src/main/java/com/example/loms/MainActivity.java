package com.example.loms;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView welcomeText;
    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authManager = new AuthManager();

        // UI 요소 초기화
        welcomeText = findViewById(R.id.welcomeText);
        Button goalListButton = findViewById(R.id.goalListButton);
        Button logoutButton = findViewById(R.id.logoutButton);
        Button settingButton = findViewById(R.id.settingButton);
        Button friendButton = findViewById(R.id.friendButton);
        Button chatButton = findViewById(R.id.chatButton);

        // 사용자 이메일 수신
        Intent intent = getIntent();
        String email = intent.getStringExtra("user_email");

        if (email == null || email.isEmpty()) {
            // 기본 메시지 설정
            welcomeText.setText(getString(R.string.default_welcome_message));
        } else {
            // 사용자 이름 추출 및 설정
            String username = email.split("@")[0];
            welcomeText.setText(getString(R.string.welcome_message, username));
        }

        // 목표 리스트 버튼 클릭 리스너
        goalListButton.setOnClickListener(v -> {
            Intent goalIntent = new Intent(MainActivity.this, GoalListActivity.class);
            goalIntent.putExtra("user_email", email);
            startActivity(goalIntent);
        });

        // 로그아웃 버튼 클릭 리스너
        logoutButton.setOnClickListener(v -> {
            authManager.signOut(); // Firebase 인증 로그아웃
            Toast.makeText(MainActivity.this, getString(R.string.logged_out_message), Toast.LENGTH_SHORT).show();
            Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(logoutIntent);
            finish(); // 로그인 화면으로 돌아가지 않도록 현재 액티비티 종료
        });

        // 환경설정 버튼 클릭 리스너
        settingButton.setOnClickListener(v -> {
            Intent settingIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingIntent);
        });

        // 친구 관리 버튼 클릭 리스너
        friendButton.setOnClickListener(v -> {
            Intent friendIntent = new Intent(MainActivity.this, FriendActivity.class);
            friendIntent.putExtra("user_email", email); // 현재 사용자 이메일 전달
            startActivity(friendIntent);
        });

        // 채팅 버튼 클릭 리스너
        chatButton.setOnClickListener(v -> {
            Intent chatIntent = new Intent(MainActivity.this, ChatActivity.class);
            chatIntent.putExtra("chatId", "chat1"); // 예제 채팅 ID 전달
            startActivity(chatIntent);
        });
    }
}
