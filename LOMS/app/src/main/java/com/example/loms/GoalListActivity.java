package com.example.loms;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class GoalListActivity extends AppCompatActivity {
    private ArrayList<Goal> goals;
    private ArrayAdapter<String> adapter;
    private RemoteDatabaseManager remoteDatabaseManager;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_list);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            userId = auth.getCurrentUser().getUid();
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        remoteDatabaseManager = new RemoteDatabaseManager();
        goals = new ArrayList<>();

        ListView goalListView = findViewById(R.id.goalListView);
        Button addGoalButton = findViewById(R.id.addGoalButton);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        goalListView.setAdapter(adapter);

        addGoalButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, NewGoalActivity.class);
            startActivity(intent);
        });

        goalListView.setOnItemClickListener((parent, view, position, id) -> {
            Goal selectedGoal = goals.get(position);
            Intent intent = new Intent(this, GoalDetailActivity.class);
            intent.putExtra("goal", selectedGoal); // Parcelable로 전달
            startActivity(intent);
        });

        loadGoals();
    }

    private void loadGoals() {
        remoteDatabaseManager.getGoals(userId, new RemoteDatabaseManager.RemoteCallback() {
            @Override
            public void onSuccess(Object result) {
                goals.clear();
                goals.addAll((ArrayList<Goal>) result);
                adapter.clear();
                for (Goal goal : goals) {
                    adapter.add(goal.getTitle());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(GoalListActivity.this, "Error loading goals: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
