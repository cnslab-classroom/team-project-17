package com.example.loms;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText emailField, passwordField;
    private Button loginButton, signUpButton; // 회원가입 버튼 추가
    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Ensure this layout exists

        emailField = findViewById(R.id.emailField); // R.id.뭐시기 부분은 xml파일에 버튼이나 입력창 같은거랑 연결된 부분. 세부적 내용은
        passwordField = findViewById(R.id.passwordField); // 파일마다 xml코드에 나와있으니 참고
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton); // 버튼 초기화
        authManager = new AuthManager();

        loginButton.setOnClickListener(v -> {
            String email = emailField.getText().toString();  // String값 받는 부분이고 이건 collection이 아니라 사용자 정보
            String password = passwordField.getText().toString();  // collection 생성 및 정보 기입은 New Goal 부분에 있음

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show(); // 입력창 비어있으면 경고문구 띄우기
                return;
            }

            authManager.signIn(email, password, new AuthManager.AuthCallback() {
                @Override
                public void onSuccess(FirebaseUser user) {
                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    // Navigate to the main activity

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class); // LoginActivity(현재 화면)에서 MainActivity(다음 화면)으로 이동
                    startActivity(intent);
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(LoginActivity.this, "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        signUpButton.setOnClickListener(v -> {
            // 회원가입 화면으로 이동
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}
