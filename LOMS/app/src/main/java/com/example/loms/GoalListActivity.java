package com.example.loms;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class GoalListActivity extends AppCompatActivity {
    private ArrayList<Goal> goals;
    private GoalAdapter adapter;
    private String userId;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_list);

        // Check if user is logged in
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            userId = auth.getCurrentUser().getUid();
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        RecyclerView goalRecyclerView = findViewById(R.id.goalRecyclerView);
        goalRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        goals = new ArrayList<>();
        adapter = new GoalAdapter(goals);
        goalRecyclerView.setAdapter(adapter);

        // Load goals from Firestore
        loadGoals();

        // Handle goal item click for editing
        adapter.setOnGoalClickListener(goal -> {
            Intent intent = new Intent(GoalListActivity.this, GoalDetailActivity.class);
            intent.putExtra("goal", goal);
            startActivity(intent);
        });

        // Handle goal item long click for deletion
        adapter.setOnGoalLongClickListener(goal -> {
            deleteGoal(goal);
            return true;
        });

        // Add goal button
        findViewById(R.id.addGoalButton).setOnClickListener(v -> {
            Intent intent = new Intent(GoalListActivity.this, NewGoalActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Loads the user's goals from Firestore.
     */
    private void loadGoals() {
        db.collection("Users").document(userId).collection("Goals")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    goals.clear();
                    for (var doc : queryDocumentSnapshots) {
                        Goal goal = doc.toObject(Goal.class);
                        goal.setId(doc.getId());
                        goals.add(goal);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to load goals", Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * Deletes a goal from Firestore and updates the UI.
     *
     * @param goal The goal to delete.
     */
    private void deleteGoal(Goal goal) {
        db.collection("Users").document(userId).collection("Goals")
                .document(goal.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(GoalListActivity.this, "Goal deleted", Toast.LENGTH_SHORT).show();
                    goals.remove(goal);
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(GoalListActivity.this, "Failed to delete goal", Toast.LENGTH_SHORT).show();
                });
    }
}
