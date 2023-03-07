package org.cinehub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // Declare the variables
    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the variables
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_passwd);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        btn_login.setOnClickListener(v -> {
            // TODO: Login
        });

        btn_register.setOnClickListener(v -> {
            // TODO: Register
        });

    }
}
