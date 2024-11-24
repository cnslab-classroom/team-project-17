package com.example.loms;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RemoteDatabaseManager {
    private final FirebaseFirestore firestore;

    public RemoteDatabaseManager() {
        firestore = FirebaseFirestore.getInstance();
    }

    /**
     * Adds or updates a goal in Firestore.
     *
     * @param userId   The user's ID.
     * @param goal     The goal object to be added or updated.
     * @param callback The callback to handle success or failure.
     */
    public void saveGoal(String userId, Goal goal, RemoteCallback<String> callback) {
        if (userId == null || goal == null || goal.getTitle() == null) {
            callback.onFailure("Invalid input: User ID or Goal data is null.");
            return;
        }

        firestore.collection("Users")
                .document(userId)
                .collection("Goals")
                .document(goal.getTitle())
                .set(goal)
                .addOnSuccessListener(aVoid -> callback.onSuccess("Goal saved successfully"))
                .addOnFailureListener(e -> callback.onFailure("Error saving goal: " + e.getMessage()));
    }

    /**
     * Retrieves all goals for a specific user.
     *
     * @param userId   The user's ID.
     * @param callback The callback to handle the result.
     */
    public void getGoals(String userId, RemoteCallback<ArrayList<Goal>> callback) {
        if (userId == null) {
            callback.onFailure("Invalid input: User ID is null.");
            return;
        }

        firestore.collection("Users")
                .document(userId)
                .collection("Goals")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<Goal> goals = new ArrayList<>();
                        task.getResult().forEach(document -> goals.add(document.toObject(Goal.class)));
                        callback.onSuccess(goals);
                    } else if (task.getException() != null) {
                        callback.onFailure("Error retrieving goals: " + task.getException().getMessage());
                    }
                })
                .addOnFailureListener(e -> callback.onFailure("Error retrieving goals: " + e.getMessage()));
    }

    /**
     * Deletes a specific goal.
     *
     * @param userId    The user's ID.
     * @param goalTitle The title of the goal to delete.
     * @param callback  The callback to handle success or failure.
     */
    public void deleteGoal(String userId, String goalTitle, RemoteCallback<String> callback) {
        if (userId == null || goalTitle == null) {
            callback.onFailure("Invalid input: User ID or Goal Title is null.");
            return;
        }

        firestore.collection("Users")
                .document(userId)
                .collection("Goals")
                .document(goalTitle)
                .delete()
                .addOnSuccessListener(aVoid -> callback.onSuccess("Goal deleted successfully"))
                .addOnFailureListener(e -> callback.onFailure("Error deleting goal: " + e.getMessage()));
    }

    /**
     * Generic interface for remote callbacks.
     *
     * @param <T> The type of the result object.
     */
    public interface RemoteCallback<T> {
        void onSuccess(T result);
        void onFailure(String errorMessage);
    }
}
