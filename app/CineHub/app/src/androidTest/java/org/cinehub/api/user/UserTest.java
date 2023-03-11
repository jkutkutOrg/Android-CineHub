package org.cinehub.api.user;

import static org.junit.Assert.assertEquals;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

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
public class UserTest {

    public static final int VALID = 1;
    public static final int INVALID = -1;
    public static final int RUNNING = 0;

    static CinehubAuth auth;
    static CinehubDB db;

    AtomicInteger running;
    ArrayList<String> msgs;

    @BeforeClass
    public static void setup() {
        auth = CinehubAPI.getAuthInstance();
        db = CinehubAPI.getDBInstance();
    }

    @Before
    public void setupTest() {
        running = new AtomicInteger(RUNNING);
        msgs = new ArrayList<>();
    }

    @After
    public void teardownTest() {
        while (running.get() == RUNNING) ;

        if (msgs.size() == 0) {
            System.out.println("No output");
        }
        else {
            System.out.println("Test output:");
            for (String s : msgs) {
                System.out.println(s);
            }
        }
        System.out.println("Test finished");
        assertEquals(VALID, running.get());
    }

    @Test
    public void getAll() {
        System.out.println("Test all started");
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
        System.out.println("Test all finished");
    }

    @Test
    public void getUser() {
        System.out.println("Test user started");
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
        System.out.println("Test user finished");
    }

    protected void print(String s) {
        msgs.add(s);
    }
}