package com.example.loms;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
    private static final int REQUEST_DETAIL = 1;

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
    //수정한 부분
    private void loadGoals() {
        db.collection("Users").document(userId).collection("Goals")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    goals.clear(); // 기존 목록을 삭제하여 화면에서 보이지 않게 함
                    for (var doc : queryDocumentSnapshots) {
                        Goal goal = doc.toObject(Goal.class);
                        goal.setId(doc.getId());
                        goals.add(goal); // 새롭게 로드된 목표만 추가
                    }
                    adapter.notifyDataSetChanged(); // RecyclerView 업데이트
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to load goals", Toast.LENGTH_SHORT).show();
                });
    }

    /*
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_DETAIL && resultCode == RESULT_OK && data != null) {
            Goal updatedGoal = (Goal) data.getSerializableExtra("updatedGoal");

            if (updatedGoal != null) {
                // 로컬 리스트에서 수정된 목표를 찾아 업데이트
                for (int i = 0; i < goals.size(); i++) {
                    if (goals.get(i).getId().equals(updatedGoal.getId())) {
                        goals.set(i, updatedGoal);
                        adapter.notifyItemChanged(i); // RecyclerView 업데이트
                        break;
                    }
                }
                Toast.makeText(this, "목표가 성공적으로 수정되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
