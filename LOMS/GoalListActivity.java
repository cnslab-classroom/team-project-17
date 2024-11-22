package com.example.loms;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.Intent;

import java.util.ArrayList;

public class GoalListActivity extends AppCompatActivity {

    private EditText goalInputField;
    private Button addGoalButton;
    private RecyclerView goalRecyclerView;
    private GoalAdapter goalAdapter;
    private ArrayList<String> goalList;

    private LocalDatabaseManager localDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_list);

        // LocalDatabaseManager 초기화
        localDatabaseManager = new LocalDatabaseManager();

        // UI 요소 초기화
        goalInputField = findViewById(R.id.goalInputField);
        addGoalButton = findViewById(R.id.addGoalButton);
        showSelectedButton = findViewById(R.id.showSelectedButton);
        goalRecyclerView = findViewById(R.id.goalRecyclerView);

        // RecyclerView 설정
        goalList = loadGoals();
        goalAdapter = new GoalAdapter(goalList, this::deleteGoal);
        goalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        goalRecyclerView.setAdapter(goalAdapter);

        // 추가 버튼 클릭 이벤트
        addGoalButton.setOnClickListener(v -> addGoal());

        //선택된 항목 표시 이벤트
        showSelectedButton.setOnClickListener(v->showSelectedGoals());
    }

    // 할 일 추가
    private void addGoal() {
        String goal = goalInputField.getText().toString().trim();
        if (!goal.isEmpty()) {
            goalList.add(goal);
            goalAdapter.notifyItemInserted(goalList.size() - 1);
            goalInputField.setText(""); // 입력 필드 초기화
            saveGoals();
        } else {
            Toast.makeText(this, "Insert your work.", Toast.LENGTH_SHORT).show();
        }
    }

    // 할 일 삭제
    private void deleteGoal(int position){
        goalList.remove(position);
        goalAdapter.notifyItemRemoved(position);
        saveGoals();
    }

    // 로컬 데이터에서 할 일 불러오기
    private ArrayList<String> loadGoals() {
        try {
            JSONArray jsonArray = localDatabaseManager.readLocalData();
            ArrayList<String> goals = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                goals.add(jsonArray.getString(i));
            }
            return goals;
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 로컬 데이터에 할 일 저장
    private void saveGoals() {
        try{
            JSONArray jsonArray = new JSONArray(goalList);
            localDatabaseManager.writeLocalData(jsonArray);
        } catch(JSONException e){
            e.printStackTrace();
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
    }

    //선택된 항목 확인
    private void showSelectedGoals(){
        ArrayList<String> selectedGoals = goalAdapter.getSelectedGoals();
        if(selectedGoals.isEmpty()){
            Toast.makeText(this,"no selected.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"selected: "+selectedGoals, Toast.LENGTH_LONG).show();
        }
    }

    // 목표 상세 화면 열기
    private void openGoalDetail(String goal) {
        Intent intent = new Intent(this, GoalDetailActivity.class);
        intent.putExtra("goalDetail", goal); // 목표 데이터를 전달
        startActivity(intent);
    }
}
