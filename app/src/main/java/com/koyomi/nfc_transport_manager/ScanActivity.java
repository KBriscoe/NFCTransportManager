package com.koyomi.nfc_transport_manager;

import android.app.Activity;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Locale;

public class ScanActivity extends Activity {

//    // List of URIs to provide to Android Beam
//    private Uri[] mFileUris = new Uri[10];
//    // Instance that returns available files from this app
//    private FileUriCallback mFileUriCallback;
//
//    private String filename;
//    // Flag to indicate that Android Beam is available
//    boolean mAndroidBeamAvailable = false;
//    NfcAdapter mNfcAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_activity);

//        NdefRecord mimeRecord = new NdefRecord(
//                NdefRecord.TNF_MIME_MEDIA,
//                "application/vnd.com.example.android.beam".getBytes(Charset.forName("US-ASCII")),
//                new byte[0], "Beam me up, Android!".getBytes(Charset.forName("US-ASCII")));

        Bundle extras = getIntent().getExtras();
//        String id = extras.getString("ID");
//        filename = id + ".tsc";
//
//        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
//        mFileUriCallback = new FileUriCallback();
//        // Set the dynamic callback for URI requests.
//        mNfcAdapter.setBeamPushUrisCallback(mFileUriCallback,this);

        // Trying to use beam
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) return; // NFC not available on this device
        String message = extras.getString("ID");
        // Possibly very simple way.
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", message.getBytes(Charset.forName("US-ASCII")));
        // More complex way that some dude on stack overflow said worked.
//        NdefRecord ndefRecord = createTextRecord(message, Locale.getDefault(), true);
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        nfcAdapter.setNdefPushMessage(ndefMessage, this);

    }

    public static NdefRecord createTextRecord(String payload, Locale locale, boolean encodeInUtf8) {
        byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));
        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        byte[] textBytes = payload.getBytes(utfEncoding);
        int utfBit = encodeInUtf8 ? 0 : (1 << 7);
        char status = (char) (utfBit + langBytes.length);
        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);
        NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
                NdefRecord.RTD_TEXT, new byte[0], data);
        return record;
    }

//    class FileUriCallback implements NfcAdapter.CreateBeamUrisCallback {
//        // List of URIs to provide to Android Beam
//        private Uri[] mFileUris = new Uri[10];
//        public FileUriCallback() {
//        }
//        /**
//         * Create content URIs as needed to share with another device
//         */
//        @Override
//        public Uri[] createBeamUris(NfcEvent event) {
//            try {
//                // catches IOException below
//                final String SEPARATOR = "::";
//                FileOutputStream fOut = openFileOutput(filename, MODE_WORLD_READABLE);
//                OutputStreamWriter osw = new OutputStreamWriter(fOut);
//
//                // Write to the file
//                osw.write(SEPARATOR);
//                osw.flush();
//                osw.close();
//
//            } catch (IOException ioe)
//            {ioe.printStackTrace();}
//            String transferFile = filename;
//            File extDir = getExternalFilesDir(null);
//            File requestFile = new File(extDir, transferFile);
//            requestFile.setReadable(true, false);
//            // Get a URI for the File and add it to the list of URIs
//            Uri fileUri = Uri.fromFile(requestFile);
//            if (fileUri != null) {
//                mFileUris[0] = fileUri;
//            } else {
//                Log.e("My Activity", "No File URI available for file.");
//            }
//            return mFileUris;
//        }
//    }
}


