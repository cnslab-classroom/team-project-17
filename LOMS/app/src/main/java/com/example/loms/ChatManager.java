package com.example.loms;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatManager {
    private final FirebaseFirestore db;
    private final FirebaseUser currentUser;

    public ChatManager() {
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    /**
     * Creates a chat room with given participants.
     *
     * @param chatId      Unique ID for the chat room.
     * @param participants List of participant user IDs.
     * @param callback    Callback to handle success or failure.
     */
    public void createChatRoom(String chatId, List<String> participants, ChatCallback callback) {
        Map<String, Object> chatRoom = new HashMap<>();
        chatRoom.put("participants", participants);

        db.collection("Chats").document(chatId)
                .set(chatRoom)
                .addOnSuccessListener(aVoid -> {
                    Log.d("ChatManager", "Chat room created successfully");
                    callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.w("ChatManager", "Error creating chat room", e);
                    callback.onFailure(e);
                });
    }

    /**
     * Sends a message to the specified chat room.
     *
     * @param chatId        Unique ID for the chat room.
     * @param messageContent Content of the message.
     * @param callback      Callback to handle success or failure.
     */
    public void sendMessage(String chatId, String messageContent, ChatCallback callback) {
        if (currentUser == null) {
            callback.onFailure(new IllegalStateException("User not logged in"));
            return;
        }

        Map<String, Object> message = new HashMap<>();
        message.put("senderId", currentUser.getUid());
        message.put("content", messageContent);
        message.put("timestamp", FieldValue.serverTimestamp());

        db.collection("Chats").document(chatId).collection("messages")
                .add(message)
                .addOnSuccessListener(aVoid -> {
                    Log.d("ChatManager", "Message sent successfully");
                    callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.w("ChatManager", "Error sending message", e);
                    callback.onFailure(e);
                });
    }

    /**
     * Listens for new messages in the specified chat room.
     *
     * @param chatId      Unique ID for the chat room.
     * @param messageListener Callback for receiving new messages.
     */
    public void listenForMessages(String chatId, MessageListener messageListener) {
        db.collection("Chats").document(chatId).collection("messages")
                .orderBy("timestamp")
                .addSnapshotListener((querySnapshot, e) -> {
                    if (e != null) {
                        Log.w("ChatManager", "Listen failed.", e);
                        return;
                    }

                    for (DocumentChange dc : querySnapshot.getDocumentChanges()) {
                        switch (dc.getType()) {
                            case ADDED:
                                messageListener.onNewMessage(dc.getDocument().getData());
                                break;
                            case MODIFIED:
                                messageListener.onMessageModified(dc.getDocument().getData());
                                break;
                            case REMOVED:
                                messageListener.onMessageRemoved(dc.getDocument().getData());
                                break;
                        }
                    }
                });
    }

    /**
     * Callback interface for chat operations.
     */
    public interface ChatCallback {
        void onSuccess();
        void onFailure(Exception e);
    }

    /**
     * Listener interface for message events.
     */
    public interface MessageListener {
        void onNewMessage(Map<String, Object> message);
        void onMessageModified(Map<String, Object> message);
        void onMessageRemoved(Map<String, Object> message);
    }
}
