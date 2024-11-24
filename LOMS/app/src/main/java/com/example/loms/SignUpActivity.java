package com.example.loms;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailField, passwordField;
    private Button signUpButton;
    private ProgressBar progressBar;
    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize UI components
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        signUpButton = findViewById(R.id.signUpButton);
        progressBar = findViewById(R.id.progressBar);
        authManager = new AuthManager();

        // Sign-up button click listener
        signUpButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (!validateInputs(email, password)) {
                return;
            }

            // Show loading and disable UI
            showLoading(true);

            authManager.signUp(email, password, new AuthManager.AuthCallback() {
                @Override
                public void onSuccess(FirebaseUser user) {
                    createUserInFirestore(user);
                }

                @Override
                public void onFailure(Exception e) {
                    showLoading(false);
                    Toast.makeText(SignUpActivity.this, getString(R.string.signup_failed, e.getMessage()), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    /**
     * Validates the input fields.
     *
     * @param email    The user's email.
     * @param password The user's password.
     * @return True if inputs are valid, false otherwise.
     */
    private boolean validateInputs(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, getString(R.string.invalid_email_format), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(this, getString(R.string.password_too_short), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * Creates a user document in Firestore.
     *
     * @param user The Firebase user.
     */
    private void createUserInFirestore(FirebaseUser user) {
        String userId = user.getUid();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("email", user.getEmail());
        userData.put("created_at", System.currentTimeMillis());

        firestore.collection("Users").document(userId).set(userData)
                .addOnSuccessListener(aVoid -> {
                    showLoading(false);
                    Toast.makeText(SignUpActivity.this, getString(R.string.signup_successful), Toast.LENGTH_SHORT).show();
                    finish(); // Return to the previous activity
                })
                .addOnFailureListener(e -> {
                    showLoading(false);
                    Toast.makeText(SignUpActivity.this, getString(R.string.firestore_error, e.getMessage()), Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * Shows or hides the loading indicator.
     *
     * @param isLoading True to show loading, false to hide.
     */
    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        signUpButton.setEnabled(!isLoading);
    }
}
