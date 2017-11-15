package com.koyomi.nfc_transport_manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.sql.*;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });
    }

    public void ConnectToDatabase() {
                try {

                    // SET CONNECTIONSTRING
                    Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                    String username = "XXXXXXXXX";
                    String password = "XXXXXX";
                    Connection DbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.188.4.83:1433/DATABASE;user=" + username + ";password=" + password);

                    Log.w("Connection", "open");
                    Statement stmt = DbConn.createStatement();
                    ResultSet reset = stmt.executeQuery(" select * from users ");


                    // EditText num = (EditText) findViewById(R.id.displaymessage);
                    // num.setText(reset.getString(1));

                    DbConn.close();

                } catch (Exception e) {
                    Log.w("Error connection", "" + e.getMessage());
                }
            }
}