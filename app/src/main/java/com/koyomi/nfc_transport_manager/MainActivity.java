package com.koyomi.nfc_transport_manager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.View;
import android.view.ViewGroup;
=======
>>>>>>> 533563f3ac41f8f221f1ca46fb9c6495d3b9cf77
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
    EditText password;
    EditText email;
    Button loginButton;
    Button signupButton;
    Button confirmSignupButton;
    Button backButton;
    Context context = this;

    final static String URL = "http://34.239.52.7/index.php";
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

        email = findViewById(R.id.emailField);
        email = findViewById(R.id.emailField);
        password = findViewById(R.id.passwordField);

        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);
        confirmSignupButton = findViewById(R.id.createButton);
        backButton = findViewById(R.id.backButton);

<<<<<<< HEAD
        loginButton.setOnClickListener(view -> {
                makeRequest(result -> {
                    try {
                        if (result.getInt("status") == 200) {
                            //Populate passID from database
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

                }, email.getText().toString(), password.getText
                        ().toString(), "login");
        });

        signupButton.setOnClickListener(View -> {
                if (viewSwitcher.getCurrentView() != loginView){
                    viewSwitcher.showPrevious();
                }else if(viewSwitcher.getCurrentView() != signupView){
                    viewSwitcher.showNext();
                }
            }
        );

        confirmSignupButton.setOnClickListener(View -> {
                    //Check validity of info

                    //SQL add profile info from text boxes.
                }
        );

        backButton.setOnClickListener(View -> {
                    if (viewSwitcher.getCurrentView() != loginView){
                        viewSwitcher.showPrevious();
                    }else if(viewSwitcher.getCurrentView() != signupView){
                        viewSwitcher.showNext();
                    }
                }
        );
=======
        loginButton.setOnClickListener(view ->
                loginRequest(result ->  {
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
                },email.getText().toString(), password.getText
                        ().toString(), "login"));


        signupButton.setOnClickListener(view ->
                signupRequest(result -> {
                  // Do whatever action is wanted after sign up.
                  System.out.print("hi");
                  System.out.print("hi");
                }, email.getText().toString(), password.getText().toString(),
                    "signup"));
>>>>>>> 533563f3ac41f8f221f1ca46fb9c6495d3b9cf77
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

                error ->
                    Toast.makeText(getApplicationContext(), "Unable to "
                        + " retrieve any data from server", Toast.LENGTH_LONG)
                        .show()

        ) {
            @Override
            protected Map<String, String> getParams() {
                    Map<String, String>  params = new HashMap<>();
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

        error ->
            Toast.makeText(getApplicationContext(), "Unable to "
                + " retrieve any data from server", Toast.LENGTH_LONG)
                .show()

    ) {
      @Override
      protected Map<String, String> getParams() {
        Map<String, String>  params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("fname", fname);
        params.put("lname", lname);
        return params;
      }
    };
    queue.add(postRequest);
  }

    public interface VolleyCallback{
        void onSuccess(JSONObject result);
    }
}