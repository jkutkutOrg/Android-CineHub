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

    /**
     * Get a movie from the database.
     *
     * @param movieId The id of the movie to get.
     * @param onSuccessValueCallback The callback to be called when the request is successful.
     * @param onFailureCallback The callback to be called when the request fails.
     */
    void getMovie(
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

    /**
     * Get a projection from the database.
     *
     * @param projectionId The id of the projection to get.
     * @param onSuccessValueCallback The callback to be called when the request is successful.
     * @param onFailureCallback The callback to be called when the request fails.
     */
    void getProjection(
        int projectionId,
        OnSuccessValueCallback<Projection> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    );

    /**
     * Get the configuration of a projection.
     *
     * @param projectionId The id of the projection to get the configuration of.
     * @param onSuccessValueCallback The callback to be called when the request is successful.
     * @param onFailureCallback The callback to be called when the request fails.
     *
     * @implNote The configuration is a 2D array of characters. Each character represents a seat.
     * The character is the state of the seat. The states are defined in {@link SpecialSeat}.
     * This method should not be mixed up with:
     * {@link CinehubDB#getRoomConfiguration(int, OnSuccessValueCallback, OnFailureCallback)}
     */
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

    /**
     * Get a reservation from the database.
     *
     * @param reservationId The id of the reservation to get.
     * @param onSuccessValueCallback The callback to be called when the request is successful.
     * @param onFailureCallback The callback to be called when the request fails.
     */
    void getReservation(
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

    /**
     * Get a room from the database.
     *
     * @param roomId The id of the room to get.
     * @param onSuccessValueCallback The callback to be called when the request is successful.
     * @param onFailureCallback The callback to be called when the request fails.
     */
    void getRoom(
        int roomId,
        OnSuccessValueCallback<Room> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    );

    /**
     * Get the configuration of a room.
     *
     * @param roomId The id of the room to get the configuration of.
     * @param onSuccessValueCallback The callback to be called when the request is successful.
     * @param onFailureCallback The callback to be called when the request fails.
     *
     * @implNote The configuration is a 2D array of chars representing the seats in a room.
     * Do not mix up this method with:
     * {@link #getProjectionConfiguration(int, OnSuccessValueCallback, OnFailureCallback)}.
     */
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

    /**
     * Get a seat reservation from the database.
     *
     * @param seatReservationId The id of the seat reservation to get.
     * @param onSuccessValueCallback The callback to be called when the request is successful.
     * @param onFailureCallback The callback to be called when the request fails.
     */
    void getSeatReservation(
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

    /**
     * Get a special seat from the database.
     *
     * @param specialSeatId The id of the special seat to get.
     * @param onSuccessValueCallback The callback to be called when the request is successful.
     * @param onFailureCallback The callback to be called when the request fails.
     */
    void getSpecialSeat(
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

    /**
     * Get a user by its id.
     *
     * @param id The id of the user.
     * @param onSuccessCallback The Callback to call when the operation is successful.
     * @param onFailureCallback The Callback to handle the error.
     */
    void getUserById(
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
