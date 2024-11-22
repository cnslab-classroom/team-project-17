package com.example.loms;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalDatabaseManager {
    private SharedPreferences sharedPreferences;

    public LocalDatabaseManager(Context context) {
        sharedPreferences = context.getSharedPreferences("LOMS", Context.MODE_PRIVATE);
    }

    public void saveString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void remove(String key) {
        sharedPreferences.edit().remove(key).apply();
    }
}
