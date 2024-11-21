package com.example.loms;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GoalDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_detail);

        // 전달받은 목표 데이터
        String goalDetail = getIntent().getStringExtra("goalDetail");

        // UI에 목표 정보 표시
        TextView goalDetailTextView = findViewById(R.id.goalDetailTextView);
        goalDetailTextView.setText(goalDetail != null ? goalDetail : "No information.");
    }
}