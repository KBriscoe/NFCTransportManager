package com.koyomi.nfc_transport_manager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.widget.ViewSwitcher;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends Activity {
    Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        TextView passIDText = findViewById(R.id.IDVALUE);
        EditText fnameField = findViewById(R.id.settingsFNameField);
        EditText lnameField = findViewById(R.id.settingsLNameField);
        EditText emailField = findViewById(R.id.settingsEmailField);
        EditText passwordField = findViewById(R.id.settingsPasswordField);
        EditText passIDField = findViewById(R.id.passIDField);

        ViewSwitcher settingsSwitcher = findViewById(R.id.settingsSwitcher);
        View mainSettingView = findViewById(R.id.mainSettingView);
        View updatePassIDView = findViewById(R.id.updatePassIDView);
        Button switchToUpdateID = findViewById(R.id.updatePassIDButton);
        Button switchToSettings = findViewById(R.id.IDPageBackButton);

        Button updateInfo = findViewById(R.id.settingsUpdateButton);
        Button updateID = findViewById(R.id.updateIDButton);

        updateInfo.setOnClickListener(view ->
            updateUserInfo(result -> {
                        try {
                            if (result.getInt("status") == 200) {
                                emailField.setText(result.getString("email"));
                                fnameField.setText(result.getString("fname"));
                                lnameField.setText(result.getString("lname"));
                                passwordField.setText("");
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.toString());
                        }
                    }, emailField.getText().toString(), passwordField.getText().toString(),
                    fnameField.getText().toString(), lnameField.getText().toString(), MainActivity.PASS_ID)
        );

        updateID.setOnClickListener(view ->
            updateID(result -> {
                try {
                    if (result.getInt("status") == 200) {
                        String newID = result.getString("id");
                        passIDText.setText(newID);
                        MainActivity.PASS_ID = newID;
                        Toast.makeText(getApplicationContext(), "Pass ID"
                                + " updated successfully", Toast.LENGTH_LONG)
                                .show();
                    }
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }, emailField.getText().toString(), passIDField.getText().toString())
        );

        String id = MainActivity.PASS_ID;
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
        }, MainActivity.PASS_ID);

        switchToUpdateID.setOnClickListener(View ->

        {
            if (settingsSwitcher.getCurrentView() != mainSettingView) {
                settingsSwitcher.showPrevious();
            } else if (settingsSwitcher.getCurrentView() != updatePassIDView) {
                settingsSwitcher.showNext();
            }
        });

        switchToSettings.setOnClickListener(View ->

        {
            if (settingsSwitcher.getCurrentView() != updatePassIDView) {
                settingsSwitcher.showNext();
            } else if (settingsSwitcher.getCurrentView() != mainSettingView) {
                settingsSwitcher.showPrevious();
            }
        });
    }

    public void updateID(final MainActivity.VolleyCallback callback, String... args) {
        final String email = args[0];
        final String id = args[1];

        if (checkValidID(id)) {
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
                    params.put("email", email);
                    params.put("id", id);
                    params.put("request", "updateID");
                    return params;
                }
            };
            queue.add(postRequest);
        }
    }

    private boolean checkValidID(String id) {
        String[] array = id.split("-");
        if (array.length == 4) {
            for (String element : array) {
                if (element.length() == 4) {
                    for (Character c : element.toCharArray()) {
                        if (!Character.isLetterOrDigit(c)) {
                            Toast.makeText(getApplicationContext(), "Not all"
                                    + " characters are alphanumeric", Toast
                                    .LENGTH_LONG).show();
                            return false;
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Not enough"
                            + " characters in: " + element, Toast.LENGTH_LONG)
                            .show();
                    return false;
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Pass ID does not have 4"
                    + " sections", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void updateUserInfo(final MainActivity.VolleyCallback callback, String... args) {
        final String email = args[0];
        final String password = args[1];
        final String fname = args[2];
        final String lname = args[3];
        final String passID = args[4];
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
                params.put("email", email);
                params.put("password", password);
                params.put("fname", fname);
                params.put("lname", lname);
                params.put("request", "updateInfo");
                return params;
            }
        };
        queue.add(postRequest);
        Toast.makeText(getApplicationContext(),
                "Successfully Updated User Info", Toast.LENGTH_LONG).show();
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
                params.put("request", "getUserInfo");
                return params;
            }
        };
        queue.add(postRequest);
    }
}
