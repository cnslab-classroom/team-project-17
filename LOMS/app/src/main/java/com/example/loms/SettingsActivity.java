package com.example.loms;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private LocalDatabaseManager localDatabaseManager;
    private Switch notificationsSwitch; // Switch 객체 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings); // 레이아웃 설정

        localDatabaseManager = new LocalDatabaseManager(this);

        // Switch 초기화
        notificationsSwitch = findViewById(R.id.notificationsSwitch);

        // 기본값을 처리하여 NullPointerException 방지
        String notificationsEnabled = localDatabaseManager.getString("notificationsEnabled");
        notificationsSwitch.setChecked("true".equals(notificationsEnabled != null ? notificationsEnabled : "false"));

        // Switch 상태 변경 시 데이터 저장
        notificationsSwitch.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            localDatabaseManager.saveString("notificationsEnabled", isChecked ? "true" : "false");
        });
    }
}
