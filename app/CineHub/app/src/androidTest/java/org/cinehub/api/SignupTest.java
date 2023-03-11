package org.cinehub.api;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SignupTest {

    public static final int VALID = 1;
    public static final int INVALID = -1;
    public static final int RUNNING = 0;

    static CinehubAuth auth;
    static CinehubDB db;

    AtomicInteger running;

    @BeforeClass
    public static void setup() {
        // Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        // assertEquals("org.cinehub", appContext.getPackageName());

        auth = CinehubAPI.getAuthInstance();
        db = CinehubAPI.getDBInstance();
    }

    @Before
    public void setupTest() {
        running = new AtomicInteger(RUNNING);
    }

    @After
    public void teardownTest() {
        while (running.get() == RUNNING) ;
        assertEquals(VALID, running.get());
        running.set(-1);
    }

    @Test
    public void signup() {
        auth.signup(
            "test",
            "testmail@mail.com",
            "test1234",
            () -> {
                running.set(VALID);
            },
            e -> {
                running.set(INVALID);
            }
        );
    }

    @Test
    public void signupError() {
        auth.signup(
            "Marta",
            "marta@gmail.com",
            "martamarta",
            () -> {
                running.set(INVALID);
            },
            e -> {
                running.set(VALID);
            }
        );
    }
}