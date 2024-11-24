package com.example.loms;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class NewGoalActivity extends AppCompatActivity {
    private RemoteDatabaseManager remoteDatabaseManager;
    private String userId;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);

        // Initialize Firebase and RemoteDatabaseManager
        remoteDatabaseManager = new RemoteDatabaseManager();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            userId = auth.getCurrentUser().getUid();
        } else {
            Toast.makeText(this, getString(R.string.user_not_logged_in), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Bind UI elements
        EditText goalTitle = findViewById(R.id.goalTitle);
        EditText goalDescription = findViewById(R.id.goalDescription);
        EditText currentWeek = findViewById(R.id.currentWeek);
        EditText finalWeek = findViewById(R.id.finalWeek);
        Button saveGoalButton = findViewById(R.id.saveGoalButton);
        progressBar = findViewById(R.id.progressBar);

        // Set button click listener
        saveGoalButton.setOnClickListener(v -> {
            String title = goalTitle.getText().toString().trim();
            String description = goalDescription.getText().toString().trim();
            String currentWeekValue = currentWeek.getText().toString().trim();
            String finalWeekValue = finalWeek.getText().toString().trim();

            if (!validateInputs(title, description, currentWeekValue, finalWeekValue)) {
                return;
            }

            int current = Integer.parseInt(currentWeekValue);
            int finalW = Integer.parseInt(finalWeekValue);

            if (current > finalW) {
                Toast.makeText(this, getString(R.string.invalid_week_values), Toast.LENGTH_SHORT).show();
                return;
            }

            Goal newGoal = new Goal(title, description, current, finalW, 0);

            // Show loading indicator
            showLoading(true);

            // Save goal to the database
            remoteDatabaseManager.saveGoal(userId, newGoal, new RemoteDatabaseManager.RemoteCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    showLoading(false);
                    Toast.makeText(NewGoalActivity.this, getString(R.string.goal_saved), Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(String errorMessage) {
                    showLoading(false);
                    Toast.makeText(NewGoalActivity.this, getString(R.string.error_saving_goal, errorMessage), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    /**
     * Validates input fields.
     *
     * @param title          Goal title.
     * @param description    Goal description.
     * @param currentWeek    Current week value.
     * @param finalWeek      Final week value.
     * @return True if inputs are valid, false otherwise.
     */
    private boolean validateInputs(String title, String description, String currentWeek, String finalWeek) {
        if (title.isEmpty() || description.isEmpty() || currentWeek.isEmpty() || finalWeek.isEmpty()) {
            Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            Integer.parseInt(currentWeek);
            Integer.parseInt(finalWeek);
        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.invalid_number_format), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * Shows or hides the loading indicator.
     *
     * @param isLoading True to show loading, false to hide.
     */
    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        findViewById(R.id.saveGoalButton).setEnabled(!isLoading);
    }
}
