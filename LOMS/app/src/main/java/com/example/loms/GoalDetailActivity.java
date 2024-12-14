package com.example.loms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class GoalDetailActivity extends AppCompatActivity {
    private Goal goal; //지역 변수로 선언을 했지만 클래스 레벨 변수로 바꿈
    //지역 변수로 하면 onActivityResult 메서드에서 오류가 생김.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_detail);

        // Retrieve Goal object from intent
        goal = (Goal) getIntent().getSerializableExtra("goal"); //바뀐 부분

        RemoteDatabaseManager remoteDatabaseManager = new RemoteDatabaseManager();

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

        //---추가한 부분----
        // 수정 버튼
        Button editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(v -> {
            Intent editIntent = new Intent(GoalDetailActivity.this, EditGoalActivity.class);
            editIntent.putExtra("goal", goal);
            startActivityForResult(editIntent, 1); // 수정된 데이터를 받기 위해 requestCode 사용
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Goal updatedGoal = (Goal) data.getSerializableExtra("updatedGoal");
            if (updatedGoal != null) {
                // 기존 Goal 삭제 후 새로운 Goal 저장
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                RemoteDatabaseManager remoteDatabaseManager = new RemoteDatabaseManager();

                remoteDatabaseManager.deleteGoal(userId, goal.getTitle(), new RemoteDatabaseManager.RemoteCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        // 기존 데이터 삭제 후 새 데이터 저장
                        remoteDatabaseManager.saveGoal(userId, updatedGoal, new RemoteDatabaseManager.RemoteCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Toast.makeText(GoalDetailActivity.this, "Goal updated successfully.", Toast.LENGTH_SHORT).show();

                                // GoalListActivity로 결과 전달
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("updatedGoal", updatedGoal);
                                setResult(RESULT_OK, resultIntent);
                                finish();
                            }

                            @Override
                            public void onFailure(String errorMessage) {
                                Toast.makeText(GoalDetailActivity.this, "Failed to save updated goal: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Toast.makeText(GoalDetailActivity.this, "Failed to delete old goal: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
    private void updateGoalDetails(Goal updatedGoal) {
        goal = updatedGoal;

        // Firestore에 수정된 데이터 저장
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid(); // 실제 사용자 ID 로직 사용
        RemoteDatabaseManager remoteDatabaseManager = new RemoteDatabaseManager();
        remoteDatabaseManager.saveGoal(userId, goal, new RemoteDatabaseManager.RemoteCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(GoalDetailActivity.this, "Goal updated and saved successfully.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(GoalDetailActivity.this, "Failed to save goal: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        // UI 업데이트
        TextView titleView = findViewById(R.id.goalTitle);
        TextView descriptionView = findViewById(R.id.goalDescription);
        TextView dateView = findViewById(R.id.goalDate);
        TextView progressView = findViewById(R.id.goalProgress);
        TextView weekView = findViewById(R.id.goalWeek);

        titleView.setText(goal.getTitle());
        descriptionView.setText(goal.getDescription());
        dateView.setText(String.format("Start: %s - End: %s", goal.getStartDate(), goal.getEndDate()));
        progressView.setText(String.format("Progress: %d%%", goal.getProgress()));
        weekView.setText(String.format("Week: %d / %d", goal.getCurrentWeek(), goal.getFinalWeek()));
    }
}
