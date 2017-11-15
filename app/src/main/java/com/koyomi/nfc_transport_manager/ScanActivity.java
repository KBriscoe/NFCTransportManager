package com.koyomi.nfc_transport_manager;

import android.app.Activity;
import android.nfc.NdefRecord;
import android.nfc.tech.MifareUltralight;
import android.os.Bundle;
import android.widget.TextView;

import java.nio.charset.Charset;

public class ScanActivity extends Activity {

    private static final String TAG = MifareUltralight.class.getSimpleName();
    private TextView passText;
    private String sentID;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_layout);
        //Setting title ID
        Bundle extras = getIntent().getExtras();
        sentID = extras.getString("ID");
        int len = extras.getInt("ID Length");
        passText = (TextView)findViewById(R.id.passText);
        passText.setText(sentID.toCharArray(), 0, len);


        NdefRecord mimeRecord = new NdefRecord(
                NdefRecord.TNF_MIME_MEDIA,
                "application/vnd.com.example.android.beam".getBytes(Charset.forName("US-ASCII")),
                new byte[0], "Beam me up, Android!".getBytes(Charset.forName("US-ASCII")));
    }
}
