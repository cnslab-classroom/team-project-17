package com.example.loms;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class GoalListActivity extends AppCompatActivity {
    private ArrayList<Goal> goals;
    private ArrayAdapter<String> adapter;
    private RemoteDatabaseManager remoteDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_list);

        // RemoteDatabaseManager 초기화
        remoteDatabaseManager = new RemoteDatabaseManager();
        goals = new ArrayList<>();

        // ListView 및 버튼 설정
        ListView goalListView = findViewById(R.id.goalListView);
        Button addGoalButton = findViewById(R.id.addGoalButton);

        // 어댑터 초기화
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        goalListView.setAdapter(adapter);

        // "Add New Goal" 버튼 클릭 이벤트
        addGoalButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, NewGoalActivity.class);
            startActivityForResult(intent, 1); // NewGoalActivity로 이동
        });

        // 항목 클릭 이벤트
        goalListView.setOnItemClickListener((parent, view, position, id) -> {
            Goal selectedGoal = goals.get(position);
            Intent intent = new Intent(this, GoalDetailActivity.class);
            intent.putExtra("goal", selectedGoal); // Parcelable로 전달
            startActivity(intent);
        });

        // Firestore에서 목표 데이터 로드
        loadGoals();
    }

    // Firestore에서 목표 데이터 가져오기
    private void loadGoals() {
        remoteDatabaseManager.getGoals(new RemoteDatabaseManager.RemoteCallback() {
            @Override
            public void onSuccess(Object result) {
                goals.clear();
                goals.addAll((ArrayList<Goal>) result); // 데이터를 업데이트
                adapter.clear();
                for (Goal goal : goals) {
                    adapter.add(goal.getTitle());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                System.err.println("Error loading goals: " + errorMessage);
            }
        });
    }

    // NewGoalActivity에서 돌아온 후 목록 새로고침
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            loadGoals(); // 목표 목록 새로고침
        }
    }
}
