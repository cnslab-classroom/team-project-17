package com.example.loms;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailField, passwordField;
    private Button signUpButton;
    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        signUpButton = findViewById(R.id.signUpButton);
        authManager = new AuthManager();

        signUpButton.setOnClickListener(v -> {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            authManager.signUp(email, password, new AuthManager.AuthCallback() {
                @Override
                public void onSuccess(FirebaseUser user) {
                    String userId = user.getUid();
                    // Firestore에 사용자 문서 생성
                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                    firestore.collection("Goals").document(userId).set(new HashMap<>())
                            .addOnSuccessListener(aVoid -> Toast.makeText(SignUpActivity.this, "Signup successful!", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(SignUpActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    finish(); // Return to the previous activity
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(SignUpActivity.this, "Signup failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
