package org.cinehub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.cinehub.utils.UserValidationUtils;

public class RegisterActivity extends AppCompatActivity {

    // Declare the variables
    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize the variables
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPasswd);
        etConfirmPassword = findViewById(R.id.etPasswdConfirm);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            boolean isValid = true;

            if (!UserValidationUtils.isUsernameValid(username)) {
                Toast.makeText(this, "The username is invalid", Toast.LENGTH_SHORT).show();
                isValid = false;
            }

            if (!UserValidationUtils.isPasswordSecure(password)) {
                Toast.makeText(this, "The password is not secure", Toast.LENGTH_SHORT).show();
                isValid = false;
            }

            if (!UserValidationUtils.arePasswordsEqual(password, confirmPassword)) {
                Toast.makeText(this, "The passwords are not equal", Toast.LENGTH_SHORT).show();
                isValid = false;
            }

            if (!UserValidationUtils.isEmailValid(email)) {
                Toast.makeText(this, "The email is invalid", Toast.LENGTH_SHORT).show();
                isValid = false;
            }

            if (isValid) {
                Toast.makeText(this, "The registration was successful", Toast.LENGTH_SHORT).show();
            }
        });

    }

}