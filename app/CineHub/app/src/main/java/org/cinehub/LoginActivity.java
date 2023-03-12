package org.cinehub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.CinehubAuth;
import org.cinehub.utils.UserValidationUtils;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;

    private CinehubAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPasswd);
        btnLogin = findViewById(R.id.btnLogic);
        btnRegister = findViewById(R.id.btnRegister);

        auth = CinehubAPI.getAuthInstance();

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            boolean isValid = true;

            if (!UserValidationUtils.isEmailValid(email)) {
                Toast.makeText(this, R.string.notification_login_error_email, Toast.LENGTH_SHORT).show();
                isValid = false;
            }

            if (password.isEmpty()) {
                Toast.makeText(this, R.string.notification_login_error_password, Toast.LENGTH_SHORT).show();
                isValid = false;
            }

            if (isValid) {
                auth.login(email, password, this::onLoginSuccess, this::onLoginError);
            }
        });

        btnRegister.setOnClickListener(v -> {
            clearFields();
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void clearFields() {
        etEmail.setText("");
        etPassword.setText("");
    }

    private void onLoginSuccess() {
        //TODO: startActivity(new Intent(this, BillBoardActivity.class));
        finish();
    }

    private void onLoginError(@NonNull String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}