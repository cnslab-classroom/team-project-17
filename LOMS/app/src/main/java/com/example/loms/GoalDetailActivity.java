package com.example.loms;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class GoalDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_detail);

        // Retrieve Goal object from intent
        Goal goal = (Goal) getIntent().getSerializableExtra("goal");

        if (goal == null) {
            // Handle missing data
            Toast.makeText(this, "Error: Goal data not available.", Toast.LENGTH_SHORT).show();
            finish(); // Close activity if no goal is passed
            return;
        }

        // Bind UI elements
        TextView titleView = findViewById(R.id.goalTitle);
        TextView descriptionView = findViewById(R.id.goalDescription);
        TextView dateView = findViewById(R.id.goalDate);
        TextView progressView = findViewById(R.id.goalProgress);
        TextView weekView = findViewById(R.id.goalWeek);

        // Set goal data to UI
        titleView.setText(goal.getTitle());
        descriptionView.setText(goal.getDescription());
        dateView.setText(String.format("Start: %s - End: %s", goal.getStartDate(), goal.getEndDate()));
        progressView.setText(String.format("Progress: %d%%", goal.getProgress()));
        weekView.setText(String.format("Week: %d / %d", goal.getCurrentWeek(), goal.getFinalWeek()));
    }
}
