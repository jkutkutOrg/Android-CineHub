package org.cinehub.api.db.reservation;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.cinehub.api.APITest;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for the Reservation API.
 *
 * @author Jkutkut
 */
@RunWith(AndroidJUnit4.class)
public class ReservationTest extends APITest {
    @Test
    public void getAll() {
        db.getReservations(
            getAllSuccessCallback(),
            getFailureCallback()
        );
    }

    @Test
    public void get() {
        int id = 0;
        db.getReservation(
            id,
            getOneSuccessCallback(),
            getFailureCallback()
        );
    }

    @Test
    public void getReservationsUser() {
        db.getUser(
            "marta@gmail.com",
            user -> db.getSeatReservationUser(
                user,
                reservations -> {
                    print("Reservations of user: " + reservations.size());
                    if (reservations.size() == 3)
                        running.set(VALID);
                    else
                        running.set(INVALID);
                },
                getFailureCallback()
            ),
            getFailureCallback()
        );
    }
}