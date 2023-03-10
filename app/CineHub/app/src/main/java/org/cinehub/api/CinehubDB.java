package org.cinehub.api;

import org.cinehub.api.model.User;
import org.cinehub.api.result.*;

import java.util.ArrayList;

/**
 * This interface represents the database of the application.
 *
 * @author Jkutkut
 */
public interface CinehubDB {
    /**
     * Get all the users in the database.
     *
     * @param onSuccessListener The listener to call when the operation is successful.
     * @param onFailureListener The listener to handle the error.
     */
    void getUsers(
        OnSuccessValueListener<ArrayList<User>> onSuccessListener,
        OnFailureListener<String> onFailureListener
    );

    /**
     * Get a user by its email.
     *
     * @param mail The email of the user.
     * @param onSuccessListener The listener to call when the operation is successful.
     * @param onFailureListener The listener to handle the error.
     */
    void getUser(
        String mail,
        OnSuccessValueListener<User> onSuccessListener,
        OnFailureListener<String> onFailureListener
    );

    /**
     * Get the current user.
     * If the user is not logged in or an error occurred, the listener will be called with the
     * appropriate error.
     *
     * @param onSuccessListener The listener to call when the operation is successful.
     * @param onFailureListener The listener to handle the error.
     */
    void whoami(
        OnSuccessValueListener<User> onSuccessListener,
        OnFailureListener<String> onFailureListener
    );
}
