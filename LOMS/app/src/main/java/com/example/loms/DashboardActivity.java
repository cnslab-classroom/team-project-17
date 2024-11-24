package com.example.loms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private Button goalListButton;
    private Button friendManagementButton;
    private Button chatButton;
    private Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize buttons
        goalListButton = findViewById(R.id.goalListButton);
        friendManagementButton = findViewById(R.id.friendManagementButton);
        chatButton = findViewById(R.id.chatButton);
        settingsButton = findViewById(R.id.settingsButton);

        // Set up button listeners
        setupButtonListeners();
    }

    /**
     * Sets up click listeners for the buttons to navigate to other activities.
     */
    private void setupButtonListeners() {
        goalListButton.setOnClickListener(v -> navigateToActivity(GoalListActivity.class));
        friendManagementButton.setOnClickListener(v -> navigateToActivity(FriendActivity.class));
        chatButton.setOnClickListener(v -> navigateToActivity(ChatActivity.class));
        settingsButton.setOnClickListener(v -> navigateToActivity(SettingsActivity.class));
    }

    /**
     * Navigates to the specified activity.
     *
     * @param activityClass The activity class to navigate to.
     */
    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(DashboardActivity.this, activityClass);
        startActivity(intent);
    }
}
