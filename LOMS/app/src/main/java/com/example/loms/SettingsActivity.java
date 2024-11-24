package com.example.loms;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private static final String NOTIFICATIONS_ENABLED_KEY = "notificationsEnabled";

    private LocalDatabaseManager localDatabaseManager;
    private Switch notificationsSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize LocalDatabaseManager
        localDatabaseManager = new LocalDatabaseManager(this);

        // Initialize Switch
        notificationsSwitch = findViewById(R.id.notificationsSwitch);

        // Load saved notification setting
        boolean isNotificationsEnabled = Boolean.parseBoolean(
                localDatabaseManager.getString(NOTIFICATIONS_ENABLED_KEY, "false"));
        notificationsSwitch.setChecked(isNotificationsEnabled);

        // Listen for changes in the switch state
        notificationsSwitch.setOnCheckedChangeListener(this::onNotificationSwitchChanged);
    }

    /**
     * Handles changes to the notification switch.
     *
     * @param buttonView The switch button view.
     * @param isChecked  The new state of the switch.
     */
    private void onNotificationSwitchChanged(CompoundButton buttonView, boolean isChecked) {
        // Save new notification state to local database
        localDatabaseManager.saveString(NOTIFICATIONS_ENABLED_KEY, String.valueOf(isChecked));

        // Show a feedback message to the user
        String message = isChecked ?
                getString(R.string.notifications_enabled) :
                getString(R.string.notifications_disabled);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
