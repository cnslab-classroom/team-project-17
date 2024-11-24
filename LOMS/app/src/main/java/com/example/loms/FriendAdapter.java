package com.example.loms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    private List<String> friendList;
    private OnFriendClickListener onFriendClickListener;

    public FriendAdapter(List<String> friendList) {
        this.friendList = new ArrayList<>(friendList); // Avoid external modifications
    }

    /**
     * Updates the friend list and refreshes the RecyclerView.
     *
     * @param newFriendList Updated list of friends.
     */
    public void updateFriends(List<String> newFriendList) {
        this.friendList.clear();
        this.friendList.addAll(newFriendList);
        notifyDataSetChanged();
    }

    /**
     * Sets the listener for item click events.
     *
     * @param listener Listener to handle click events.
     */
    public void setOnFriendClickListener(OnFriendClickListener listener) {
        this.onFriendClickListener = listener;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_friend, parent, false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        String friendName = friendList.get(position);
        holder.friendNameTextView.setText(friendName);

        // Set click listener for each item
        holder.itemView.setOnClickListener(v -> {
            if (onFriendClickListener != null) {
                onFriendClickListener.onFriendClick(friendName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    /**
     * ViewHolder class for displaying each friend item.
     */
    public static class FriendViewHolder extends RecyclerView.ViewHolder {
        private final TextView friendNameTextView;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            friendNameTextView = itemView.findViewById(R.id.friendNameTextView);
        }
    }

    /**
     * Interface for handling friend item click events.
     */
    public interface OnFriendClickListener {
        void onFriendClick(String friendName);
    }
}
