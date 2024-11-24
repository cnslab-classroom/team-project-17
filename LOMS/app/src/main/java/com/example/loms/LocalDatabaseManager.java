package com.example.loms;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalDatabaseManager {
    private static final String PREF_NAME = "LOMS";
    private SharedPreferences sharedPreferences;

    public LocalDatabaseManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Save a string value
    public LocalDatabaseManager saveString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
        return this;
    }

    // Retrieve a string value
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    // Save an integer value
    public LocalDatabaseManager saveInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
        return this;
    }

    // Retrieve an integer value
    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    // Save a boolean value
    public LocalDatabaseManager saveBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
        return this;
    }

    // Retrieve a boolean value
    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    // Save a float value
    public LocalDatabaseManager saveFloat(String key, float value) {
        sharedPreferences.edit().putFloat(key, value).apply();
        return this;
    }

    // Retrieve a float value
    public float getFloat(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    // Remove a specific key
    public LocalDatabaseManager remove(String key) {
        sharedPreferences.edit().remove(key).apply();
        return this;
    }

    // Clear all stored data
    public void clearAll() {
        sharedPreferences.edit().clear().apply();
    }
}
