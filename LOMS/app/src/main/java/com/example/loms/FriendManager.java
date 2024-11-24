package com.example.loms;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendManager {
    private static final String TAG = "FriendManager";
    private final FirebaseFirestore db;

    public FriendManager() {
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Gets the user document from Firestore.
     *
     * @param userId The user ID to fetch the document for.
     * @return A Task containing the user's document.
     */
    private Task<DocumentSnapshot> getUserDocument(String userId) {
        return db.collection("Users").document(userId).get();
    }

    /**
     * Adds a friend to the user's friend list.
     *
     * @param currentUserId The current user's ID.
     * @param friendUserId  The friend's user ID to add.
     * @param callback      Callback to handle success or failure.
     */
    public void addFriend(String currentUserId, String friendUserId, FriendActionCallback callback) {
        if (currentUserId == null || currentUserId.isEmpty() || friendUserId == null || friendUserId.isEmpty()) {
            callback.onFailure(new IllegalArgumentException("Invalid user IDs provided."));
            return;
        }

        getUserDocument(currentUserId)
                .continueWithTask(task -> {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        return db.collection("Users").document(currentUserId)
                                .update("friends", FieldValue.arrayUnion(friendUserId));
                    } else {
                        Map<String, Object> data = new HashMap<>();
                        data.put("friends", Arrays.asList(friendUserId));
                        return db.collection("Users").document(currentUserId).set(data);
                    }
                })
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Friend added successfully: " + friendUserId);
                    callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding friend: " + friendUserId, e);
                    callback.onFailure(e);
                });
    }

    /**
     * Removes a friend from the user's friend list.
     *
     * @param currentUserId The current user's ID.
     * @param friendUserId  The friend's user ID to remove.
     * @param callback      Callback to handle success or failure.
     */
    public void removeFriend(String currentUserId, String friendUserId, FriendActionCallback callback) {
        if (currentUserId == null || currentUserId.isEmpty() || friendUserId == null || friendUserId.isEmpty()) {
            callback.onFailure(new IllegalArgumentException("Invalid user IDs provided."));
            return;
        }

        getUserDocument(currentUserId)
                .continueWithTask(task -> {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        return db.collection("Users").document(currentUserId)
                                .update("friends", FieldValue.arrayRemove(friendUserId));
                    } else {
                        Log.w(TAG, "Document does not exist for user: " + currentUserId);
                        return com.google.android.gms.tasks.Tasks.forResult(null);
                    }
                })
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Friend removed successfully: " + friendUserId);
                    callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error removing friend: " + friendUserId, e);
                    callback.onFailure(e);
                });
    }

    /**
     * Fetches the list of friends for the user.
     *
     * @param currentUserId The current user's ID.
     * @param listener      Listener to handle the fetched friend list.
     */
    public void getFriends(String currentUserId, OnFriendsFetchedListener listener) {
        if (currentUserId == null || currentUserId.isEmpty()) {
            Log.e(TAG, "Invalid user ID provided.");
            listener.onFetched(new ArrayList<>());
            return;
        }

        getUserDocument(currentUserId)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
                        DocumentSnapshot document = task.getResult();
                        List<String> friends = (List<String>) document.get("friends");
                        listener.onFetched(friends != null ? friends : new ArrayList<>());
                    } else {
                        Log.e(TAG, "Error fetching friends for user: " + currentUserId);
                        listener.onFetched(new ArrayList<>());
                    }
                });
    }

    /**
     * Callback interface for friend actions (add/remove).
     */
    public interface FriendActionCallback {
        void onSuccess();
        void onFailure(Exception e);
    }

    /**
     * Listener interface for fetching the friend list.
     */
    public interface OnFriendsFetchedListener {
        void onFetched(List<String> friends);
    }
}
