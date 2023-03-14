package org.cinehub.api;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.cinehub.api.result.OnFailureCallback;
import org.cinehub.api.result.OnSuccessValueCallback;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is the base class for all API tests.
 *
 * @author Jkutkut
 */
@RunWith(AndroidJUnit4.class)
public class APITest {

    public static final int VALID = 1;
    public static final int INVALID = -1;
    public static final int RUNNING = 0;

    protected static CinehubAuth auth;
    protected static CinehubDB db;
    protected static CinehubStorage storage;

    protected AtomicInteger running;
    protected ArrayList<String> msgs;

    @BeforeClass
    public static void setup() {
        auth = CinehubAPI.getAuthInstance();
        db = CinehubAPI.getDBInstance();
        storage = CinehubAPI.getStorageInstance();
    }

    @Before
    public void setupTest() {
        running = new AtomicInteger(RUNNING);
        msgs = new ArrayList<>();
    }

    @After
    public void teardownTest() {
        while (running.get() == RUNNING) ; // Yep, just wait

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

    protected void print(String s) {
        msgs.add(s);
    }

    protected <T> OnSuccessValueCallback<ArrayList<T>> getAllSuccessCallback() {
        return lst -> {
            if (lst.isEmpty()) {
                print("lst.isEmpty()");
                running.set(INVALID);
            }
            else {
                print("lst.size() = " + lst.size());
                running.set(VALID);
            }
        };
    }

    protected <T> OnSuccessValueCallback<T> getOneSuccessCallback() {
        return obj -> {
            if (obj == null) {
                print("obj == null");
                running.set(INVALID);
            }
            else {
                print("obj = " + obj);
                running.set(VALID);
            }
        };
    }

    protected OnFailureCallback<String> getFailureCallback() {
        return e -> {
            print("Error: " + e);
            running.set(INVALID);
        };
    }
}