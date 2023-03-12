package org.cinehub.api;

import org.cinehub.api.model.Movie;
import org.cinehub.api.model.Projection;
import org.cinehub.api.model.Reservation;
import org.cinehub.api.model.Room;
import org.cinehub.api.model.SpecialSeat;
import org.cinehub.api.model.SeatReservation;
import org.cinehub.api.model.User;
import org.cinehub.api.result.*;

import java.util.ArrayList;

/**
 * This interface represents the database of the application.
 *
 * @author Jkutkut
 */
public interface CinehubDB {

    // ** Movies **

    /**
     * Get all movies from the database.
     *
     * @param onSuccessValueCallback The callback to be called when the request is successful.
     * @param onFailureCallback The callback to be called when the request fails.
     */
    void getMovies(
        OnSuccessValueCallback<ArrayList<Movie>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    );

    void getMovie( // TODO doc
        int movieId,
        OnSuccessValueCallback<Movie> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    );

    // ** Projection **

    /**
     * Get all the projections from the database.
     *
     * @param onSuccessValueCallback The callback to be called when the request is successful.
     * @param onFailureCallback The callback to be called when the request fails.
     */
    void getProjections(
        OnSuccessValueCallback<ArrayList<Projection>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    );

    void getProjection( // TODO doc
        int projectionId,
        OnSuccessValueCallback<Projection> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    );

    void getProjectionConfiguration(
        int projectionId,
        OnSuccessValueCallback<char[][]> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    );

    // ** Reservation **

    /**
     * Get all the reservations from the database.
     *
     * @param onSuccessValueCallback The callback to be called when the request is successful.
     * @param onFailureCallback The callback to be called when the request fails.
     */
    void getReservations(
        OnSuccessValueCallback<ArrayList<Reservation>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    );

    void getReservation( // TODO doc
        int reservationId,
        OnSuccessValueCallback<Reservation> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    );

    // ** Room **

    /**
     * Get all the rooms from the database.
     *
     * @param onSuccessValueCallback The callback to be called when the request is successful.
     * @param onFailureCallback The callback to be called when the request fails.
     */
    void getRooms(
        OnSuccessValueCallback<ArrayList<Room>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    );

    void getRoom( // TODO doc
        int roomId,
        OnSuccessValueCallback<Room> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    );

    void getRoomConfiguration(
        int roomId,
        OnSuccessValueCallback<char[][]> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    );

    // ** SeatReservation **

    /**
     * Get all the seat reservations from the database.
     *
     * @param onSuccessValueCallback The callback to be called when the request is successful.
     * @param onFailureCallback The callback to be called when the request fails.
     */
    void getSeatReservations(
        OnSuccessValueCallback<ArrayList<SeatReservation>> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    );

    void getSeatReservation( // TODO doc
        int seatReservationId,
        OnSuccessValueCallback<SeatReservation> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    );

    // ** SpecialSeat **
    /**
     * Get all the special seats from the database.
     *
     * @param onSuccessValueCallback The callback to be called when the request is successful.
     * @param onFailureCallback The callback to be called when the request fails.
     */
    void getSpecialSeats(
            OnSuccessValueCallback<ArrayList<SpecialSeat>> onSuccessValueCallback,
            OnFailureCallback<String> onFailureCallback
    );

    void getSpecialSeat( // TODO doc
        int specialSeatId,
        OnSuccessValueCallback<SpecialSeat> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    );

    // ** Users **

    /**
     * Get all the users in the database.
     *
     * @param onSuccessCallback The Callback to call when the operation is successful.
     * @param onFailureCallback The Callback to handle the error.
     */
    void getUsers(
        OnSuccessValueCallback<ArrayList<User>> onSuccessCallback,
        OnFailureCallback<String> onFailureCallback
    );

    /**
     * Get a user by its email.
     *
     * @param mail The email of the user.
     * @param onSuccessCallback The Callback to call when the operation is successful.
     * @param onFailureCallback The Callback to handle the error.
     */
    void getUser(
        String mail,
        OnSuccessValueCallback<User> onSuccessCallback,
        OnFailureCallback<String> onFailureCallback
    );

    void getUserById( // TODO doc
        int id,
        OnSuccessValueCallback<User> onSuccessCallback,
        OnFailureCallback<String> onFailureCallback
    );

    /**
     * Get the current user.
     * If the user is not logged in or an error occurred, the Callback will be called with the
     * appropriate error.
     *
     * @param onSuccessCallback The Callback to call when the operation is successful.
     * @param onFailureCallback The Callback to handle the error.
     */
    void whoami(
        OnSuccessValueCallback<User> onSuccessCallback,
        OnFailureCallback<String> onFailureCallback
    );
}
