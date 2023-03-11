package org.cinehub.api;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class APITest {

    public static final int VALID = 1;
    public static final int INVALID = -1;
    public static final int RUNNING = 0;

    protected static CinehubAuth auth;
    protected static CinehubDB db;

    protected AtomicInteger running;
    protected ArrayList<String> msgs;

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

    protected void print(String s) {
        msgs.add(s);
    }
}