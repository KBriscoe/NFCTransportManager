package com.koyomi.nfc_transport_manager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.sql.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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