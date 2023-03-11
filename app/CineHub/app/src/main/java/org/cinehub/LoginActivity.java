package org.cinehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.cinehub.utils.UserValidationUtils;

public class LoginActivity extends AppCompatActivity {

    // Declare the variables
    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize the variables
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPasswd);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();

            boolean isValid = true;

            if (!UserValidationUtils.isEmailValid(email)) {
                Toast.makeText(this, "The email is invalid", Toast.LENGTH_SHORT).show();
                isValid = false;
                startActivity(new Intent(this, BillBoardActivity.class));
            }

            if (isValid) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            }

        });

        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

    }
}
