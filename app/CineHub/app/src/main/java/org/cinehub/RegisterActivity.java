package org.cinehub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    // Declare the variables
    private EditText et_username;
    private EditText et_email;
    private EditText et_password;
    private EditText et_confirm_password;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize the variables
        et_username = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_passwd);
        et_confirm_password = findViewById(R.id.et_passwd_confirm);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(v -> {
            // TODO: Register
        });

    }
}