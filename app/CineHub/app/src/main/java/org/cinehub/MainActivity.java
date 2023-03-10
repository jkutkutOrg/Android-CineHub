package org.cinehub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.CinehubAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(v -> ft());
    }

    private void ft() {
        CinehubAuth auth = CinehubAPI.getAuthInstance();

//        auth.signup(
//            "test user",
//            "test@gmail.com",
//            "test123",
//            () -> {
//                System.out.println("User created");
//            },
//            (error) -> {
//                System.out.println("Error: " + error);
//            }
//        );
//        auth.autoLogin(
//            email -> {
//                System.out.println("Already logged with email: " + email);
//            }
//        );
        auth.login(
            "test@gmail.com",
            "test123",
            () -> {
                System.out.println("Logged in successfully");
            },
            (error) -> {
                System.out.println("Error: " + error);
            }
        );
    }
}