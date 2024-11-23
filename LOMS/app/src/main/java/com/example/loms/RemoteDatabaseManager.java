package com.example.loms;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RemoteDatabaseManager {
    private final FirebaseFirestore firestore;

    public RemoteDatabaseManager() {
        firestore = FirebaseFirestore.getInstance();
    }

    // 목표 추가 메서드
    public void addGoal(String userId, String title, Goal goal, RemoteCallback callback) {
        firestore.collection("Goals")
                .document(userId)
                .collection("title")
                .document(title)
                .set(goal)
                .addOnSuccessListener(aVoid -> callback.onSuccess("Goal added successfully"))
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    // 사용자별 목표 가져오기 메서드
    public void getGoals(String userId, RemoteCallback callback) {
        firestore.collection("Goals")
                .document(userId) // 사용자 ID 문서 접근
                .collection("title") // title 서브컬렉션 접근
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<Goal> goals = new ArrayList<>();
                        task.getResult().forEach(document -> goals.add(document.toObject(Goal.class)));
                        callback.onSuccess(goals);
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                })
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    // RemoteCallback 인터페이스 정의
    public interface RemoteCallback {
        void onSuccess(Object result);
        void onFailure(String errorMessage);
    }
}
