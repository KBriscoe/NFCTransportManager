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

<<<<<<< HEAD
        scanMode.setOnClickListener(View -> {
            Intent intent = new Intent(ProfileActivity.this, ScanActivity.class);
            //passID = email.getText().toString();
            //int IDLength = passID.length();
            //String sendingID = passID;
            //intent.putExtra("ID", sendingID);
            //intent.putExtra("ID Length", IDLength);
=======
        Bundle extras = getIntent().getExtras();
        String id = extras.getString("ID");

        scanMode.setOnClickListener(View -> {
            Intent intent = new Intent(ProfileActivity.this, ScanActivity.class);
>>>>>>> Tyler
            startActivity(intent);
        });

        settings.setOnClickListener(View ->{
            Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
<<<<<<< HEAD
=======
            intent.putExtra("ID", id);
>>>>>>> Tyler
            startActivity(intent);
        });
    }
}
