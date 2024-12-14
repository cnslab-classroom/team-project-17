package com.example.loms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {
    private final ArrayList<Goal> goals;
    private OnGoalClickListener onGoalClickListener;
    private OnGoalLongClickListener onGoalLongClickListener;

    public GoalAdapter(ArrayList<Goal> goals) {
        this.goals = goals;
    }

    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_goal, parent, false);
        return new GoalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
        Goal goal = goals.get(position);
        holder.title.setText(goal.getTitle());

        // Set progress visualization
        ProgressDrawable progressDrawable = new ProgressDrawable(goal.getProgress());
        progressDrawable.setBounds(0, 0, holder.progressCircle.getWidth(), holder.progressCircle.getHeight());
        holder.progressCircle.setBackground(progressDrawable);

        holder.itemView.setOnClickListener(v -> {
            if (onGoalClickListener != null) onGoalClickListener.onClick(goal);
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (onGoalLongClickListener != null) return onGoalLongClickListener.onLongClick(goal);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

    public void setOnGoalClickListener(OnGoalClickListener listener) {
        this.onGoalClickListener = listener;
    }

    public void setOnGoalLongClickListener(OnGoalLongClickListener listener) {
        this.onGoalLongClickListener = listener;
    }

    public interface OnGoalClickListener {
        void onClick(Goal goal);
    }

    public interface OnGoalLongClickListener {
        boolean onLongClick(Goal goal);
    }

    static class GoalViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final View progressCircle;

        public GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.goalTitle);
            progressCircle = itemView.findViewById(R.id.progressCircle); // Ensure this matches your layout
        }
    }


    //---밑에는 추가된 부분---

}
