package org.cinehub.api.db.user;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.cinehub.api.APITest;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for the User API.
 *
 * @author Jkutkut
 */
@RunWith(AndroidJUnit4.class)
public class UserTest extends APITest {
    @Test
    public void getAll() {
        db.getUsers(
            getAllSuccessCallback(),
            getFailureCallback()
        );
    }

    @Test
    public void getUser() {
        db.getUser(
            "marta@gmail.com",
            getOneSuccessCallback(),
            getFailureCallback()
        );
    }

    @Test
    public void getUserById() {
        db.getUserById(
            0,
            getOneSuccessCallback(),
            getFailureCallback()
        );
    }
}