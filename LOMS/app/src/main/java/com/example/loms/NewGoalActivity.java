package com.example.loms;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class NewGoalActivity extends AppCompatActivity {
    private RemoteDatabaseManager remoteDatabaseManager;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);

        remoteDatabaseManager = new RemoteDatabaseManager();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            userId = auth.getCurrentUser().getUid();
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        EditText goalTitle = findViewById(R.id.goalTitle);
        EditText goalDescription = findViewById(R.id.goalDescription);
        EditText currentWeek = findViewById(R.id.currentWeek);
        EditText finalWeek = findViewById(R.id.finalWeek);
        Button saveGoalButton = findViewById(R.id.saveGoalButton);

        saveGoalButton.setOnClickListener(v -> {
            String title = goalTitle.getText().toString().trim();
            String description = goalDescription.getText().toString().trim();
            String currentWeekValue = currentWeek.getText().toString().trim();
            String finalWeekValue = finalWeek.getText().toString().trim();

            if (title.isEmpty() || description.isEmpty() || currentWeekValue.isEmpty() || finalWeekValue.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int current = Integer.parseInt(currentWeekValue);
            int finalW = Integer.parseInt(finalWeekValue);
            Goal newGoal = new Goal(title, description, current, finalW, 0);

            remoteDatabaseManager.addGoal(userId, title, newGoal, new RemoteDatabaseManager.RemoteCallback() {
                @Override
                public void onSuccess(Object result) {
                    Toast.makeText(NewGoalActivity.this, "Goal saved successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText(NewGoalActivity.this, "Error saving goal: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
