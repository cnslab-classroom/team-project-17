package com.example.loms;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditGoalActivity extends AppCompatActivity {

    private EditText titleInput, descriptionInput, currentWeekInput, finalWeekInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goal);

        RemoteDatabaseManager remoteDatabaseManager = new RemoteDatabaseManager();

        // Retrieve Goal object
        Goal goal = (Goal) getIntent().getSerializableExtra("goal");

        if (goal == null) {
            Toast.makeText(this, "Error: Goal data not available.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Bind UI elements
        titleInput = findViewById(R.id.editTitle);
        descriptionInput = findViewById(R.id.editDescription);
        currentWeekInput = findViewById(R.id.editCurrentWeek);
        finalWeekInput = findViewById(R.id.editFinalWeek);

        // Populate fields with existing goal data
        titleInput.setText(goal.getTitle());
        descriptionInput.setText(goal.getDescription());
        currentWeekInput.setText(String.valueOf(goal.getCurrentWeek()));
        finalWeekInput.setText(String.valueOf(goal.getFinalWeek()));

        // Save button
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            if (validateInputs()) {
                goal.setTitle(titleInput.getText().toString());
                goal.setDescription(descriptionInput.getText().toString());
                goal.setCurrentWeek(Integer.parseInt(currentWeekInput.getText().toString()));
                goal.setFinalWeek(Integer.parseInt(finalWeekInput.getText().toString()));

                // Firestore 저장
                String userId = "exampleUserId"; // 실제 사용자 ID 로직 사용
                remoteDatabaseManager.saveGoal(userId, goal, new RemoteDatabaseManager.RemoteCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("updatedGoal", goal);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Toast.makeText(EditGoalActivity.this, "Failed to save goal: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Bind UI elements for progress buttons
        Button progressOneThird = findViewById(R.id.progressOneThird);
        Button progressTwoThird = findViewById(R.id.progressTwoThird);
        Button progressFull = findViewById(R.id.progressFull);

        View progressCircle = findViewById(R.id.progressCircle);

        // Set button click listeners
        progressOneThird.setOnClickListener(v -> {
            goal.setProgress(33); // Set progress to 33%
            updateProgressCircle(progressCircle, 33);
        });

        progressTwoThird.setOnClickListener(v -> {
            goal.setProgress(66); // Set progress to 66%
            updateProgressCircle(progressCircle, 66);
        });

        progressFull.setOnClickListener(v -> {
            goal.setProgress(100); // Set progress to 100%
            updateProgressCircle(progressCircle, 100);
        });
    }

    private boolean validateInputs() {
        if (titleInput.getText().toString().isEmpty() ||
                descriptionInput.getText().toString().isEmpty() ||
                currentWeekInput.getText().toString().isEmpty() ||
                finalWeekInput.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            int currentWeek = Integer.parseInt(currentWeekInput.getText().toString());
            int finalWeek = Integer.parseInt(finalWeekInput.getText().toString());

            if (currentWeek <= 0 || finalWeek <= 0 || currentWeek > finalWeek) {
                Toast.makeText(this, "Week data is invalid. Ensure current week ≤ final week.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Numeric fields must contain valid numbers.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void updateProgressCircle(View progressCircle, int progress) {
        progressCircle.post(() -> {
            ProgressDrawable drawable = new ProgressDrawable(progress);
            drawable.setBounds(0, 0, progressCircle.getWidth(), progressCircle.getHeight());
            progressCircle.setBackground(drawable); // Ensure compatibility with Drawable
        });
    }
}
