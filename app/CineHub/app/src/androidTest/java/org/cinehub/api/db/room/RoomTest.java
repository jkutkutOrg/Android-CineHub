package org.cinehub.api.db.room;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.cinehub.api.APITest;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for the Room API.
 *
 * @author Jkutkut
 */
@RunWith(AndroidJUnit4.class)
public class RoomTest extends APITest {
    @Test
    public void getAll() {
        db.getRooms(
            getAllSuccessCallback(),
            getFailureCallback()
        );
    }

    @Test
    public void get() {
        int id = 0;
        db.getRoom(
            id,
            getOneSuccessCallback(),
            getFailureCallback()
        );
    }
}