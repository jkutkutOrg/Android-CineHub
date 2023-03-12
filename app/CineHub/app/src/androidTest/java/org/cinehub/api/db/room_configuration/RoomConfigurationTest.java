package org.cinehub.api.db.room_configuration;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.cinehub.api.APITest;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for the RoomConfiguration API.
 *
 * @author Jkutkut
 */
@RunWith(AndroidJUnit4.class)
public class RoomConfigurationTest extends APITest {
    @Test
    public void getAll() {
        db.getRoomConfigurations(
            getAllSuccessCallback(),
            getFailureCallback()
        );
    }

    @Test
    public void get() {
        int id = 0;
        db.getRoomConfiguration(
            id,
            getOneSuccessCallback(),
            getFailureCallback()
        );
    }
}