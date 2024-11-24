package com.example.loms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText emailField, passwordField;
    private Button loginButton, signUpButton;
    private ProgressBar progressBar;
    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI components
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);
        progressBar = findViewById(R.id.progressBar); // 로딩 상태 표시

        authManager = new AuthManager();

        // Auto-login if user is already authenticated
        autoLogin();

        // Login button click listener
        loginButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (!validateInputs(email, password)) {
                return;
            }

            // Show progress bar and disable inputs
            showLoading(true);

            authManager.signIn(email, password, new AuthManager.AuthCallback() {
                @Override
                public void onSuccess(FirebaseUser user) {
                    showLoading(false);
                    Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                    navigateToMain();
                }

                @Override
                public void onFailure(Exception e) {
                    showLoading(false);
                    Toast.makeText(LoginActivity.this, getString(R.string.login_failed, e.getMessage()), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Sign-up button click listener
        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Automatically logs in if the user is already authenticated.
     */
    private void autoLogin() {
        FirebaseUser currentUser = authManager.getCurrentUser();
        if (currentUser != null) {
            navigateToMain();
        }
    }

    /**
     * Navigates to the main activity.
     */
    private void navigateToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Prevent back navigation to login screen
    }

    /**
     * Validates the input fields.
     *
     * @param email    The user's email.
     * @param password The user's password.
     * @return True if inputs are valid, false otherwise.
     */
    private boolean validateInputs(String email, String password) {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.setError(getString(R.string.invalid_email));
            emailField.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            passwordField.setError(getString(R.string.empty_password));
            passwordField.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Shows or hides the loading indicator.
     *
     * @param isLoading True to show loading, false to hide.
     */
    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        loginButton.setEnabled(!isLoading);
        signUpButton.setEnabled(!isLoading);
    }
}
