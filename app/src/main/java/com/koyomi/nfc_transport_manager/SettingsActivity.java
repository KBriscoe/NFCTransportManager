package com.koyomi.nfc_transport_manager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

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

        Bundle extras = getIntent().getExtras();
        String id = extras.getString("ID");
        passIDText.setText(id.toCharArray(), 0, id.toCharArray().length);

        getUserInfo(result -> {
            try {
                if (result.getInt("status") == 200) {
                    emailField.setText(result.getString("email"));
                    fnameField.setText(result.getString("fname"));
                    lnameField.setText(result.getString("lname"));
                }
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }, extras.getString("ID"));
    }

    public void getUserInfo(final MainActivity.VolleyCallback callback, String... args) {
        final String passID = args[0];
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, MainActivity.URL,
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
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
