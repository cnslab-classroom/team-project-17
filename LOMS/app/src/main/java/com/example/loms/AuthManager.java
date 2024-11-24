package com.example.loms;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.util.Log;

public class AuthManager {
    private FirebaseAuth firebaseAuth;
    private static final String SECRET_KEY = "MySecretKey12345"; // Replace with a secure key

    public AuthManager() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    /**
     * Gets the currently logged-in user.
     *
     * @return FirebaseUser or null if no user is logged in.
     */
    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    /**
     * Logs out the currently logged-in user.
     */
    public void signOut() {
        firebaseAuth.signOut();
    }

    /**
     * Signs up a new user with email and AES-encrypted password.
     *
     * @param email    User's email.
     * @param password User's password (unencrypted).
     * @param callback Callback for success or failure.
     */
    public void signUp(String email, String password, AuthCallback callback) {
        try {
            // Encrypt the password using AES
            String encryptedPassword = AESUtils.encrypt(password, SECRET_KEY);

            // Create a new user with email and encrypted password
            firebaseAuth.createUserWithEmailAndPassword(email, encryptedPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            callback.onSuccess(firebaseAuth.getCurrentUser());
                        } else {
                            callback.onFailure(task.getException());
                        }
                    });
        } catch (Exception e) {
            Log.e("AuthManager", "Encryption error: " + e.getMessage());
            callback.onFailure(e);
        }
    }

    /**
     * Logs in an existing user with email and AES-decrypted password.
     *
     * @param email    User's email.
     * @param password User's password (unencrypted).
     * @param callback Callback for success or failure.
     */
    public void signIn(String email, String password, AuthCallback callback) {
        try {
            // Encrypt the password using AES
            String encryptedPassword = AESUtils.encrypt(password, SECRET_KEY);

            // Sign in with encrypted password
            firebaseAuth.signInWithEmailAndPassword(email, encryptedPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            callback.onSuccess(firebaseAuth.getCurrentUser());
                        } else {
                            callback.onFailure(task.getException());
                        }
                    });
        } catch (Exception e) {
            Log.e("AuthManager", "Encryption error: " + e.getMessage());
            callback.onFailure(e);
        }
    }

    /**
     * Callback interface for authentication events.
     */
    public interface AuthCallback {
        void onSuccess(FirebaseUser user);
        void onFailure(Exception e);
    }
}
