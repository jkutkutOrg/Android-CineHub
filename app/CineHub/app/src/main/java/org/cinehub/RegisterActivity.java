package org.cinehub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

            if (!isUsernameValid(username)) {
                Toast.makeText(this, "The username is invalid", Toast.LENGTH_SHORT).show();
                isValid = false;
            }

            if (!isPasswordSecure(password)) {
                Toast.makeText(this, "The password is not secure", Toast.LENGTH_SHORT).show();
                isValid = false;
            }

            if (!arePasswordsEqual(password, confirmPassword)) {
                Toast.makeText(this, "The passwords are not equal", Toast.LENGTH_SHORT).show();
                isValid = false;
            }

            if (!isEmailValid(email)) {
                Toast.makeText(this, "The email is invalid", Toast.LENGTH_SHORT).show();
                isValid = false;
            }

            if (isValid) {
                Toast.makeText(this, "The registration was successful", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Checks if the username is valid (contains only letters, numbers, underscores and dashes
     * and has a length between 3 and 15 characters)
     *
     * @param username The username to check
     * @return true if the username is valid, false otherwise
     */
    private boolean isUsernameValid(String username) {
        String regex = "^[a-zA-Z0-9_-]{3,15}$";
        return username.matches(regex);
    }

    /**
     * Checks if the password is secure (contains at least one lowercase letter, one uppercase
     * letter, one digit and one special character
     *
     * @param password The password to check
     * @return true if the password is secure, false otherwise
     */
    private boolean isPasswordSecure(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(regex);
    }

    /**
     * Checks if the password and the confirmation password are equal
     *
     * @param password The password
     * @param confirmPassword The confirmation password
     * @return true if the passwords are equal, false otherwise
     */
    private boolean arePasswordsEqual(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    /**
     * Checks if the email is valid
     *
     * @param email The email to check
     * @return true if the email is valid, false otherwise
     */
    private boolean isEmailValid(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)\\.(.+)$";
        return email.matches(regex);
    }
}