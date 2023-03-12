package org.cinehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.CinehubAuth;
import org.cinehub.utils.UserValidationUtils;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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

            int errorMessageResId = -1;

            if (!UserValidationUtils.isUsernameValid(username)) {
                errorMessageResId = R.string.notification_register_error_username;
            } else if (!UserValidationUtils.isPasswordSecure(password)) {
                errorMessageResId = R.string.notification_register_error_password_sec;
            } else if (!UserValidationUtils.arePasswordsEqual(password, confirmPassword)) {
                errorMessageResId = R.string.notification_register_error_password;
            } else if (!UserValidationUtils.isEmailValid(email)) {
                errorMessageResId = R.string.notification_register_error_email;
            }

            if (errorMessageResId != -1) {
                Toast.makeText(this, errorMessageResId, Toast.LENGTH_SHORT).show();
            } else {
                CinehubAuth auth = CinehubAPI.getAuthInstance();
                auth.signup(
                        username, email, password, this::onSignupSuccess, this::onSignupError
                );
            }
        });

    }

    private void onSignupSuccess() {
        cleanFields();
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void onSignupError(@NonNull String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private void cleanFields() {
        etUsername.setText("");
        etEmail.setText("");
        etPassword.setText("");
        etConfirmPassword.setText("");
    }
}