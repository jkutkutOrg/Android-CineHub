package org.cinehub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.CinehubAuth;
import org.cinehub.api.CinehubDB;
import org.cinehub.api.model.User;

public class MainActivity extends AppCompatActivity {
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(v -> ft());

        CinehubAPI.getAuthInstance().autoLogin(
            email -> {
                System.out.println("Already logged with email: " + email);
                user = email;
            },
            error -> {}
        );
    }

    private void ft() {
        CinehubDB db = CinehubAPI.getDBInstance();

        db.getUsers(
            users -> {
                System.out.println("Users:");
                for (User user : users) {
                    System.out.println(String.format(
                        "Name: %s, Email: %s",
                        user.getName(),
                        user.getEmail()
                    ));
                }
            },
            (error) -> {
                System.out.println("Error: " + error);
            }
        );

        db.getUser(
            "marta@gmail.com",
            user -> {
                System.out.println("User:");
                System.out.println(String.format(
                        "Name: %s, Email: %s",
                        user.getName(),
                        user.getEmail()
                ));
            },
            (error) -> {
                System.out.println("Error: " + error);
            }
        );

        db.whoami(
            user -> {
                System.out.println("Me:");
                System.out.println(String.format(
                        "Name: %s, Email: %s",
                        user.getName(),
                        user.getEmail()
                ));
            },
            null
        );
    }
}