package com.koyomi.nfc_transport_manager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends Activity {

    TextView passIDText = findViewById(R.id.IDVALUE);
    EditText fnameField = findViewById(R.id.settingsFNameField);
    EditText lnameField = findViewById(R.id.settingsLNameField);
    EditText emailField = findViewById(R.id.settingsEmailField);
    Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        Intent intent = getIntent();

        getUserInfo(result -> {
            try {
                if (result.getInt("status") == 200) {

                }
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }, intent.getStringExtra("ID"));
    }

    public void getUserInfo(final MainActivity.VolleyCallback callback, String... args) {
        final String passID = args[0];
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, MainActivity.URL,
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getInt("status") == 200) {
                            emailField.setText(object.getString("email"));
                            fnameField.setText(object.getString("fname"));
                            lnameField.setText(object.getString("lname"));
                        }
                        callback.onSuccess(object);
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                },

                error -> Toast.makeText(getApplicationContext(),
                        "Unable to retrieve any data from server", Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", passID);
                return params;
            }
        };
        queue.add(postRequest);
    }
}
