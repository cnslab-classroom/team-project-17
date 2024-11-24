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

        public GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.goalTitle); // Ensure this ID matches your `item_goal.xml`
        }
    }
}
