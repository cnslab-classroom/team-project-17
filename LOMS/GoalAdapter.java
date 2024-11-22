package com.example.loms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;

// GoalAdapter: RecyclerView를 위한 어댑터로, 목표(goal) 리스트를 관리하고 선택 상태를 표시함
public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {

    // RecyclerView에 표시될 목표 리스트
    private final ArrayList<String> goalList;

    // 사용자가 선택한 항목의 인덱스를 저장하는 HashSet (중복 방지)
    private final HashSet<Integer> selectedItems;

    // GoalClickListener 인터페이스를 통해 외부에서 클릭 이벤트를 처리할 수 있도록 연결
    private final GoalClickListener goalClickListener;

    // 생성자: 목표 리스트와 클릭 리스너를 초기화하고 선택 항목 저장소(HashSet)를 생성
    public GoalAdapter(ArrayList<String> goalList, GoalClickListener goalClickListener) {
        this.goalList = goalList;
        this.goalClickListener = goalClickListener;
        this.selectedItems = new HashSet<>();
    }

    // ViewHolder 생성 시 호출. 목록의 각 항목 뷰를 생성하고 초기화
    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 단순 텍스트 항목 레이아웃을 Inflate하여 ViewHolder에 전달
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new GoalViewHolder(view);
    }

    // ViewHolder에 데이터를 바인딩. 각 위치(position)에 해당하는 데이터를 뷰에 설정
    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
        // 현재 항목의 목표 데이터를 가져옴
        String goal = goalList.get(position);

        // 항목의 텍스트를 설정
        holder.goalTextView.setText(goal);

        // 선택 상태에 따라 배경색 변경
        if (selectedItems.contains(position)) {
            holder.itemView.setBackgroundColor(color.LTGRAY); // 선택된 경우 회색
        } else {
            holder.itemView.setBackgroundColor(color.TRANSPARENT); // 선택되지 않은 경우 투명
        }

        // 항목 클릭 이벤트 처리
        holder.itemView.setOnClickListener(v -> {
            // 선택 상태를 토글
            if (selectedItems.contains(position)) {
                selectedItems.remove(position); // 선택 해제
            } else {
                selectedItems.add(position); // 선택 추가
            }
            notifyItemChanged(position); // UI 업데이트를 위해 항목 변경 알림
        });
    }

    // RecyclerView에 표시할 항목 수 반환
    @Override
    public int getItemCount() {
        return goalList.size();
    }

    // 선택된 목표를 반환하는 메서드
    public ArrayList<String> getSelectedGoals() {
        ArrayList<String> selectedGoals = new ArrayList<>();
        // 선택된 각 인덱스에 해당하는 목표를 리스트에 추가
        for (int position : selectedItems) {
            selectedGoals.add(goalList.get(position));
        }
        return selectedGoals;
    }

    // GoalViewHolder: 각 항목 뷰를 관리하는 클래스
    static class GoalViewHolder extends RecyclerView.ViewHolder {
        TextView goalTextView; // 항목의 텍스트뷰

        // 생성자: View 요소 초기화
        GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            goalTextView = itemView.findViewById(android.R.id.text1); // 기본 텍스트뷰 ID를 사용
        }
    }
}
