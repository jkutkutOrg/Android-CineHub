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
