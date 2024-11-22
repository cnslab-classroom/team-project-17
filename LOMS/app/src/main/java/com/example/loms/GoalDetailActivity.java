package com.example.loms;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GoalDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_detail);

        Goal goal = (Goal) getIntent().getSerializableExtra("goal");

        TextView titleView = findViewById(R.id.goalTitle);
        TextView descriptionView = findViewById(R.id.goalDescription);
        TextView dateView = findViewById(R.id.goalDate);

        titleView.setText(goal.getTitle());
        descriptionView.setText(goal.getDescription());
        dateView.setText("Start: " + goal.getStartDate() + " - End: " + goal.getEndDate());
    }
}
