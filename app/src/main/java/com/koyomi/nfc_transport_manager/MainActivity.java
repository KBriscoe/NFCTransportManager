package com.koyomi.nfc_transport_manager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String passID;
    EditText password;
    EditText email;
    Button loginButton;
    Button signupButton;
    Context context = this;

    final static String URL = "http://34.239.52.7/index.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        email = findViewById(R.id.emailField);
        email = findViewById(R.id.emailField);
        password = findViewById(R.id.passwordField);

        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeRequest(new VolleyCallback(){
                    @Override
                    public void onSuccess(JSONObject result) {
                        try {
                            if (result.getInt("status") == 200) {
                                //Assign correct PassID based on username here
                                passID = email.getText().toString();
                                int IDLength = passID.length();
                                String sendingID = passID;
                                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                                intent.putExtra("ID", sendingID);
                                intent.putExtra("ID Length", IDLength);
                                startActivity(intent);
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.toString());
                        }
                    }
                },email.getText().toString(), password.getText
                        ().toString(), "login");
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeRequest(new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {

                    }
                },email.getText().toString(), password.getText
                        ().toString(), "signup");
            }
        });
    }

    public void makeRequest(final VolleyCallback callback, String... args) {
        final String email = args[0];
        final String password = args[1];
        final String login = args[2];
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), object.getString
                                    ("message"), Toast.LENGTH_LONG).show();
                            callback.onSuccess(object);
                        } catch (Exception ex) {
                            System.out.println(ex.toString());
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                    Map<String, String>  params = new HashMap<>();
                    params.put("email", email);
                    params.put("password", password);
                    params.put("login", login);

                    return params;
            }
        };
        queue.add(postRequest);
    }

    public interface VolleyCallback{
        void onSuccess(JSONObject result);
    }
}