package com.koyomi.nfc_transport_manager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String passID;
    private int IDLength;
    EditText loginPassword;
    EditText loginEmail;
    EditText signupEmail;
    EditText signupPassword;
    EditText signupFirstName;
    EditText signupLastName;
    Button loginButton;
    Button switchToSignup;
    Button confirmSignupButton;
    Button backButton;
    Context context = this;

    final static String URL = "http://ec2-54-165-172-110.compute-1.amazonaws.com/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        ViewSwitcher viewSwitcher = findViewById(R.id.viewSwitcher);
        View loginView = findViewById(R.id.loginView);
        View signupView = findViewById(R.id.signupView);

        loginEmail = findViewById(R.id.emailField);
        loginPassword = findViewById(R.id.passwordField);

        loginButton = findViewById(R.id.loginButton);
        switchToSignup = findViewById(R.id.signupButton);
        confirmSignupButton = findViewById(R.id.createButton);
        backButton = findViewById(R.id.backButton);

        loginButton.setOnClickListener(view ->
                loginRequest(result -> {
                    try {
                        if (result.getInt("status") == 200) {
//                            passID = result.getString("id");
//                            IDLength = passID.length();
                            switchToScanActivity();
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                }, loginEmail.getText().toString(), loginPassword.getText().toString()));

        switchToSignup.setOnClickListener(View -> {
                    if (viewSwitcher.getCurrentView() != loginView) {
                        viewSwitcher.showPrevious();
                    } else if (viewSwitcher.getCurrentView() != signupView) {
                        viewSwitcher.showNext();
                    }
                }
        );

        confirmSignupButton.setOnClickListener(view ->
                signupRequest(result -> {
                    try {
//                        passID = result.getString("id");
//                        IDLength = passID.length();
                        switchToScanActivity();
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                }, signupEmail.getText().toString(), signupPassword.getText().toString(),
                        signupFirstName.getText().toString(), signupLastName.getText().toString()));

        backButton.setOnClickListener(View -> {
                    if (viewSwitcher.getCurrentView() != loginView) {
                        viewSwitcher.showPrevious();
                    } else if (viewSwitcher.getCurrentView() != signupView) {
                        viewSwitcher.showNext();
                    }
                }
        );
    }

    private void switchToScanActivity() {
        Intent intent = new Intent(MainActivity.this, ScanActivity.class);
        intent.putExtra("ID", passID);
        intent.putExtra("ID Length", IDLength);
        startActivity(intent);
    }

    public void loginRequest(final VolleyCallback callback, String... args) {
        final String email = args[0];
        final String password = args[1];
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), object.getString
                                ("message"), Toast.LENGTH_LONG).show();
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
                params.put("password", password);
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void signupRequest(final VolleyCallback callback, String... args) {
        final String email = args[0];
        final String password = args[1];
        final String fname = args[2];
        final String lname = args[3];
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), object.getString
                                ("message"), Toast.LENGTH_LONG).show();
                        callback.onSuccess(object);
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                },

                error -> Toast.makeText(getApplicationContext(),
                        "Unable to  retrieve any data from server", Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                params.put("fname", fname);
                params.put("lname", lname);
                return params;
            }
        };
        queue.add(postRequest);
    }

    public interface VolleyCallback {
        void onSuccess(JSONObject result);
    }
}