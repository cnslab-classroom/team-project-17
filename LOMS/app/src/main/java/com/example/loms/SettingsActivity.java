package com.example.loms;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private LocalDatabaseManager localDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        localDatabaseManager = new LocalDatabaseManager(this);

        Switch notificationsSwitch = findViewById(R.id.notificationsSwitch);
        notificationsSwitch.setChecked(localDatabaseManager.getString("notificationsEnabled").equals("true"));

        notificationsSwitch.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            localDatabaseManager.saveString("notificationsEnabled", isChecked ? "true" : "false");
        });
    }
}
