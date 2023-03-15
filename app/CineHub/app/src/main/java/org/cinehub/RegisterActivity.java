package org.cinehub;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.CinehubAuth;
import org.cinehub.utils.UserValidationUtils;

public class RegisterActivity extends NavActivity {

    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPasswd);
        etConfirmPassword = findViewById(R.id.etPasswdConfirm);
        Button btnRegister = findViewById(R.id.btnRegister);

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
                        username, email, password, this::finish, this::onSignupError
                );
            }
        });
    }

    private void onSignupError(@NonNull String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}