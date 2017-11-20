package com.koyomi.nfc_transport_manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ProfileActivity extends Activity{
    Button scanMode;
    Button settings;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_landing_activity);

        scanMode = findViewById(R.id.scanButton);
        settings = findViewById(R.id.settingsButton);

        scanMode.setOnClickListener(View -> {
            Intent intent = new Intent(ProfileActivity.this, ScanActivity.class);
            startActivity(intent);
        });

        settings.setOnClickListener(View ->{
            Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}
