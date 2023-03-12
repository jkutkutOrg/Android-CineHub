package org.cinehub.api.auth;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.cinehub.api.APITest;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for the Auth API.
 * @author Jkutkut
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