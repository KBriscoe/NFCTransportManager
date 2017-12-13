package com.koyomi.nfc_transport_manager;

import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;

import java.nio.charset.Charset;

public class ScanActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_activity);
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) return;
        String message = MainActivity.PASS_ID;
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", message.getBytes(Charset.forName("US-ASCII")));
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        nfcAdapter.setNdefPushMessage(ndefMessage, this);

    }
}


