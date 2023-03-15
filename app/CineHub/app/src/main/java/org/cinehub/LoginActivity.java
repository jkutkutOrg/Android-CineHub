package org.cinehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.CinehubAuth;
import org.cinehub.utils.UserValidationUtils;

public class LoginActivity extends NavActivity {


    private EditText etEmail;
    private EditText etPassword;

    private CinehubAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPasswd);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);

        auth = CinehubAPI.getAuthInstance();

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            boolean isValid = true;

            if (!UserValidationUtils.isEmailValid(email)) {
                etEmail.setError(getString(R.string.error_email));
                isValid = false;
            }
            if (password.isEmpty()) {
                etPassword.setError(getString(R.string.notification_login_error_password));
                isValid = false;
            }

            if (isValid) {
                auth.login(email, password,
                    () -> advanceActivity(() -> {
                        finish();
                        return new Intent(this, HomeActivity.class);
                    }),
                    this::onLoginError
                );
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

    private void onLoginError(String error) {
        etEmail.setError(getString(R.string.error_login));
        etPassword.setText("");
    }
}