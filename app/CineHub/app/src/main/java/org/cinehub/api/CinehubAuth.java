package org.cinehub.api;

import org.cinehub.api.model.User;
import org.cinehub.api.result.OnFailureCallback;
import org.cinehub.api.result.OnSuccessCallback;
import org.cinehub.api.result.OnSuccessValueCallback;

/**
 * Interface to handle the authentication of the user.
 *
 * @author Jkutkut
 */
public interface CinehubAuth {
    /**
     * Attempts to login a user.
     *
     * @param email email of the user
     * @param password password of the user
     * @param onSuccessCallback listener to be called on success
     * @param onFailureCallback listener to be called on failure
     */
    void login(
        String email, String password,
        OnSuccessCallback onSuccessCallback,
        OnFailureCallback<String> onFailureCallback
    );

    /**
     * Attempts to signup a user.
     *
     * @param name name of the user
     * @param email email of the user
     * @param password password of the user
     * @param onSuccessCallback listener to be called on success
     * @param onFailureCallback listener to be called on failure
     */
    void signup(
        String name, String email, String password,
        OnSuccessCallback onSuccessCallback,
        OnFailureCallback<String> onFailureCallback
    );

    /**
     * Attempts to login a user automatically.
     *
     * @param onSuccessListener listener to be called if the user is already logged in
     * @param onFailureListener listener to be called if the user is not logged in
     */
    void autoLogin(
        OnSuccessValueCallback<String> onSuccessListener,
        OnFailureCallback<String> onFailureListener
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
