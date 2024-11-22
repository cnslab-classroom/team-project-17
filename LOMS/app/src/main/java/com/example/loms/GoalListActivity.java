package com.example.loms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View; // View import 추가
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class GoalListActivity extends AppCompatActivity {
    private ArrayList<Goal> goals;
    private ArrayAdapter<String> adapter;
    private RemoteDatabaseManager remoteDatabaseManager;

    private TextView GoalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_list);

        //textbox
        GoalText = findViewById(R.id.Goaltext);

        //username 받기
        Intent intent = getIntent();
        String email = intent.getStringExtra("user_email");

        //텍스트
        if( email != null ) {
            String username = email.split("@")[0];
            GoalText.setText(username+"님의 목표리스트!");
        }
        // RemoteDatabaseManager 초기화
        remoteDatabaseManager = new RemoteDatabaseManager();
        goals = new ArrayList<>();

        // ListView 설정
        ListView GoalListView = findViewById(R.id.GoalListView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        GoalListView.setAdapter(adapter);

        // 항목 클릭 이벤트 처리
        GoalListView.setOnItemClickListener((parent, view, position, id) -> {
            Goal selectedGoal = goals.get(position);
            Intent intent1 = new Intent(this, GoalDetailActivity.class);
            intent1.putExtra("goal", selectedGoal);
            startActivity(intent1);
        });

        // 목표 데이터 로드
        loadGoals();
    }

    private void loadGoals() {
        remoteDatabaseManager.getGoals(new RemoteDatabaseManager.RemoteCallback() {
            @Override
            public void onSuccess(Object result) {
                goals.clear();
                goals.addAll((ArrayList<Goal>) result); // unchecked cast가 발생할 수 있으니 안전하게 처리
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
}
