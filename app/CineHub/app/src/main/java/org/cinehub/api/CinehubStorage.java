package org.cinehub.api;

import org.cinehub.api.model.Movie;
import org.cinehub.api.model.User;
import org.cinehub.api.result.OnFailureCallback;
import org.cinehub.api.result.OnSuccessCallback;
import org.cinehub.api.result.OnSuccessValueCallback;

/**
 * Interface to handle the storage db.
 *
 * @author Jkutkut
 */
public interface CinehubStorage {
    /**
     * Obtains the banner url for the given movie.
     *
     * @param movie The movie to get the banner for.
     * @param onSuccessValueCallback The callback to be called when the request is successful.
     * @param onFailureCallback The callback to be called when the request fails.
     */
    void getBanner(
        Movie movie,
        OnSuccessValueCallback<String> onSuccessValueCallback,
        OnFailureCallback<String> onFailureCallback
    );
}
