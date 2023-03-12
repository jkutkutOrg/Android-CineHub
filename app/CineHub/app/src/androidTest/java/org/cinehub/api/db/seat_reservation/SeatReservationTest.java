package org.cinehub.api.db.seat_reservation;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.cinehub.api.APITest;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for the SeatReservation API.
 *
 * @author Jkutkut
 */
@RunWith(AndroidJUnit4.class)
public class SeatReservationTest extends APITest {
    @Test
    public void getAll() {
        db.getSeatReservations(
            getAllSuccessCallback(),
            getFailureCallback()
        );
    }

    @Test
    public void get() {
        int id = 0;
        db.getSeatReservation(
            id,
            getOneSuccessCallback(),
            getFailureCallback()
        );
    }
}