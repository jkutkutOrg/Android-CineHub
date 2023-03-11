package org.cinehub.api.user;

import static org.junit.Assert.assertEquals;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.cinehub.api.APITest;
import org.cinehub.api.CinehubAPI;
import org.cinehub.api.CinehubAuth;
import org.cinehub.api.CinehubDB;
import org.cinehub.api.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UserTest extends APITest {
    @Test
    public void getAll() {
        db.getUsers(
            users -> {
                if (users == null) {
                    print("users == null");
                    running.set(INVALID);
                }
                else if (users.isEmpty()) {
                    print("users.isEmpty()");
                    running.set(INVALID);
                }
                else {
                    print("users.size() = " + users.size());
                    running.set(VALID);
                }
            },
            e -> {
                print("Error: " + e);
                running.set(INVALID);
            }
        );
    }

    @Test
    public void getUser() {
        db.getUser(
            "marta@gmail.com",
            user -> {
                print("user = " + user);
                if (user == null) {
                    running.set(INVALID);
                }
                else {
                    print("user.getEmail() = " + user.getEmail());
                    print("user.getName() = " + user.getName());
                    running.set(VALID);
                }
            },
            e -> {
                print("Error: " + e);
                running.set(INVALID);
            }
        );
    }
}