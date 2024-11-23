package com.example.loms;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NewGoalActivity extends AppCompatActivity {
    private RemoteDatabaseManager remoteDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);

        // RemoteDatabaseManager 초기화
        remoteDatabaseManager = new RemoteDatabaseManager();

        // UI 요소 참조
        EditText goalTitle = findViewById(R.id.goalTitle);
        EditText goalDescription = findViewById(R.id.goalDescription);
        Button saveGoalButton = findViewById(R.id.saveGoalButton);

        // 저장 버튼 클릭 이벤트
        saveGoalButton.setOnClickListener(v -> {
            String title = goalTitle.getText().toString().trim();
            String description = goalDescription.getText().toString().trim();

            // 입력값 검증
            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Goal 객체 생성
            Goal newGoal = new Goal(title, description);

            // Firestore에 목표 저장
            remoteDatabaseManager.addGoal(newGoal, new RemoteDatabaseManager.RemoteCallback() {
                @Override
                public void onSuccess(Object result) {
                    Toast.makeText(NewGoalActivity.this, "Goal saved successfully!", Toast.LENGTH_SHORT).show();
                    finish(); // 액티비티 종료
                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText(NewGoalActivity.this, "Error saving goal: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
