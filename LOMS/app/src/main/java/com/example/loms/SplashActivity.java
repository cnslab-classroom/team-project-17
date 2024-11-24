package com.example.loms;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 2000; // Duration in milliseconds
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(this::navigateNext, SPLASH_DURATION);
    }

    /**
     * Determines the next activity and navigates to it.
     */
    private void navigateNext() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // User is logged in, navigate to MainActivity
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra("user_email", currentUser.getEmail());
            startActivity(intent);
        } else {
            // User is not logged in, navigate to LoginActivity
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        finish(); // Close SplashActivity
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null); // Prevent delayed execution if activity is destroyed
        }
    }
}
