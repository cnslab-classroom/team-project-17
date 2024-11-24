package com.example.loms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FriendActivity extends AppCompatActivity {
    private FriendManager friendManager;
    private FriendAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        // Initialize FriendManager
        friendManager = new FriendManager();

        // Connect UI elements
        EditText friendIdInput = findViewById(R.id.friendIdInput);
        Button addFriendButton = findViewById(R.id.addFriendButton);
        Button removeFriendButton = findViewById(R.id.removeFriendButton);
        RecyclerView friendListRecyclerView = findViewById(R.id.friendListRecyclerView);

        // Set up RecyclerView
        friendListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get current user ID
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String currentUserId = (currentUser != null) ? currentUser.getUid() : null;

        if (currentUserId == null) {
            // Redirect to login if authentication fails
            Toast.makeText(this, "User is not authenticated. Please login again.", Toast.LENGTH_SHORT).show();
            Intent loginIntent = new Intent(FriendActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
            return;
        }

        // Fetch friends list
        friendManager.getFriends(currentUserId, friends -> {
            adapter = new FriendAdapter(friends);
            friendListRecyclerView.setAdapter(adapter);
        });

        // Add friend button click listener
        addFriendButton.setOnClickListener(v -> {
            String friendUserId = friendIdInput.getText().toString().trim();
            if (friendUserId.isEmpty()) {
                Toast.makeText(this, "Please enter a valid Friend ID.", Toast.LENGTH_SHORT).show();
                return;
            }

            friendManager.addFriend(currentUserId, friendUserId, new FriendManager.FriendActionCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(FriendActivity.this, "Friend added successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("FriendActivity", "Friend added: " + friendUserId);

                    // Update RecyclerView
                    friendManager.getFriends(currentUserId, updatedFriends -> {
                        adapter.updateFriends(updatedFriends);
                    });
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(FriendActivity.this, "Failed to add friend.", Toast.LENGTH_SHORT).show();
                    Log.e("FriendActivity", "Error adding friend", e);
                }
            });
        });

        // Remove friend button click listener
        removeFriendButton.setOnClickListener(v -> {
            String friendUserId = friendIdInput.getText().toString().trim();
            if (friendUserId.isEmpty()) {
                Toast.makeText(this, "Please enter a valid Friend ID.", Toast.LENGTH_SHORT).show();
                return;
            }

            friendManager.removeFriend(currentUserId, friendUserId, new FriendManager.FriendActionCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(FriendActivity.this, "Friend removed successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("FriendActivity", "Friend removed: " + friendUserId);

                    // Update RecyclerView
                    friendManager.getFriends(currentUserId, updatedFriends -> {
                        adapter.updateFriends(updatedFriends);
                    });
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(FriendActivity.this, "Failed to remove friend.", Toast.LENGTH_SHORT).show();
                    Log.e("FriendActivity", "Error removing friend", e);
                }
            });
        });
    }
}
