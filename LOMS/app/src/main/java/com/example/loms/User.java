package com.example.loms;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String email;
    private List<String> friends;

    /**
     * Default constructor required for Firestore deserialization.
     */
    public User() {
        this.friends = new ArrayList<>();
    }

    /**
     * Constructor for creating a new user.
     *
     * @param email   The user's email.
     * @param friends The user's list of friends.
     */
    public User(String email, List<String> friends) {
        this.email = email;
        this.friends = friends != null ? friends : new ArrayList<>();
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getFriends() {
        return friends != null ? friends : new ArrayList<>();
    }

    public void setFriends(List<String> friends) {
        this.friends = friends != null ? friends : new ArrayList<>();
    }

    /**
     * Adds a friend to the user's friend list.
     *
     * @param friendEmail The email of the friend to add.
     */
    public void addFriend(String friendEmail) {
        if (friends == null) {
            friends = new ArrayList<>();
        }
        if (!friends.contains(friendEmail)) {
            friends.add(friendEmail);
        }
    }

    /**
     * Removes a friend from the user's friend list.
     *
     * @param friendEmail The email of the friend to remove.
     */
    public void removeFriend(String friendEmail) {
        if (friends != null) {
            friends.remove(friendEmail);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", friends=" + friends +
                '}';
    }
}
