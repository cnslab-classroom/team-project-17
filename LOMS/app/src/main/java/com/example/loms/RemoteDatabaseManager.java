package com.example.loms;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;

public class RemoteDatabaseManager {
    private FirebaseFirestore firestore;
    private CollectionReference goalsRef;

    public RemoteDatabaseManager() {
        firestore = FirebaseFirestore.getInstance();
        goalsRef = firestore.collection("Goals");
    }

    public void addGoal(Goal goal, RemoteCallback callback) {
        goalsRef.document(goal.getId()).set(goal)
                .addOnSuccessListener(aVoid -> callback.onSuccess("Goal added successfully"))
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public void updateGoal(Goal goal, RemoteCallback callback) {
        goalsRef.document(goal.getId()).set(goal)
                .addOnSuccessListener(aVoid -> callback.onSuccess("Goal updated successfully"))
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public void getGoals(RemoteCallback callback) {
        goalsRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<Goal> goals = new ArrayList<>();
                        task.getResult().forEach(document -> goals.add(document.toObject(Goal.class)));
                        callback.onSuccess(goals);
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    public interface RemoteCallback {
        void onSuccess(Object result);
        void onFailure(String errorMessage);
    }
}
