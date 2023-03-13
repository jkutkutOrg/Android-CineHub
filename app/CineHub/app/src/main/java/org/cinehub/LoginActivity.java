package org.cinehub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.cinehub.api.model.User;
import org.cinehub.utils.UserValidationUtils;

public class LoginActivity extends NavActivity {

    public static final String EXTRA_USER = "User";

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
            }

            if (isValid) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                advanceActivity(() ->
                        new Intent(this, BillBoardActivity.class)
                                .putExtra(EXTRA_USER, new User(email)));
            }

        });

        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

    }
}
