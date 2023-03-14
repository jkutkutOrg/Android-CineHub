package org.cinehub.api.db_insert;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.cinehub.api.APITest;
import org.cinehub.api.model.Projection;
import org.cinehub.api.model.Seat;
import org.cinehub.api.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * Tests for the reservation of seats with the API.
 *
 * @author Jkutkut
 */
@RunWith(AndroidJUnit4.class)
public class ReservationTest extends APITest {
    @Test
    public void addReservation() {
        User usr = new User("marta@gmail.com", "Marta");
        Projection projection = new Projection(0, 0, "2023-03-30T18:42:00.000Z");
        ArrayList<Seat> seats = new ArrayList<>();
        seats.add(new Seat(2, 0));
        seats.add(new Seat(2, 1));
        seats.add(new Seat(2, 2));

        db.addReservation(
            usr,
            projection,
            seats,
            () -> running.set(VALID),
            getFailureCallback()
        );
    }

    @Test
    public void addInvalidReservation() {
        User usr = new User("marta@gmail.com", "Marta");
        Projection projection = new Projection(0, 0, "2023-03-30T18:42:00.000Z");
        ArrayList<Seat> seats = new ArrayList<>();
        seats.add(new Seat(5, 5));
        db.addReservation(
                usr,
                projection,
                seats,
                () -> running.set(INVALID),
                e -> {
                    print(e);
                    running.set(VALID);
                }
        );
    }
}