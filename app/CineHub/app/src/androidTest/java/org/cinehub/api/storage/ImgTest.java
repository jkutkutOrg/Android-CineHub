package org.cinehub.api.storage;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.cinehub.api.APITest;
import org.cinehub.api.model.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for the Auth API.
 * @author Jkutkut
 */
@RunWith(AndroidJUnit4.class)
public class ImgTest extends APITest {

    @Test
    public void getPhoto() {
        db.getMovie(
            0,
            movie -> {
                storage.getBanner(
                    movie,
                    url -> {
                        print("Banner url: " + url);
                        running.set(VALID);
                    },
                    error -> {
                        print("Error: " + error);
                        print("Banner url: " + movie.getBanner());
                        running.set(INVALID);
                    }
                );
            },
            getFailureCallback()
        );
    }

    @Test
    public void getInvalidPhoto() {
        storage.getBanner(
            new Movie("Invalid movie", "INVALID", "invalid.png", 3),
            url -> {
                print("Invalid banner url: " + url);
                running.set(INVALID);
            },
            error -> {
                print("Error: " + error);
                running.set(VALID);
            }
        );
    }
}