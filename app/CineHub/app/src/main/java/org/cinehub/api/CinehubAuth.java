package org.cinehub.api;

import org.cinehub.api.result.OnFailureListener;
import org.cinehub.api.result.OnSuccessListener;
import org.cinehub.api.result.OnSuccessValueListener;

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
     * @param onSuccessListener listener to be called on success
     * @param onFailureListener listener to be called on failure
     */
    void login(
        String email, String password,
        OnSuccessListener onSuccessListener,
        OnFailureListener<String> onFailureListener
    );

    /**
     * Attempts to signup a user.
     *
     * @param name name of the user
     * @param email email of the user
     * @param password password of the user
     * @param onSuccessListener listener to be called on success
     * @param onFailureListener listener to be called on failure
     */
    void signup(
        String name, String email, String password,
        OnSuccessListener onSuccessListener,
        OnFailureListener<String> onFailureListener
    );

    /**
     * Attempts to login a user automatically.
     *
     * @param onSuccessListener listener to be called if the user is already logged in
     * @apiNote This method does nothing if the user is not already logged in.
     */
    void autoLogin(
        OnSuccessValueListener<String> onSuccessListener
    );
}
