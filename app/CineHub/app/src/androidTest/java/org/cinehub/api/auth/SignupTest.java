package org.cinehub.api.auth;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.cinehub.api.APITest;
import org.cinehub.api.CinehubAPI;
import org.cinehub.api.CinehubAuth;
import org.cinehub.api.CinehubDB;
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
public class SignupTest extends APITest {

    @Test
    public void signup() {
        auth.signup(
            "test",
            "testmail@mail.com",
            "test1234",
            () -> running.set(VALID),
            e -> running.set(INVALID)
        );
    }

    @Test
    public void signupError() {
        auth.signup(
            "Marta",
            "marta@gmail.com",
            "martamarta",
            () -> running.set(INVALID),
            e -> running.set(VALID)
        );
    }
}