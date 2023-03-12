package org.cinehub.api.db.movie;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.cinehub.api.APITest;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for the Movie API.
 *
 * @author Jkutkut
 */
@RunWith(AndroidJUnit4.class)
public class MovieTest extends APITest {
    @Test
    public void getAll() {
        db.getMovies(
            getAllSuccessCallback(),
            getFailureCallback()
        );
    }

    @Test
    public void get() {
        int id = 0;
        db.getMovie(
            id,
            getOneSuccessCallback(),
            getFailureCallback()
        );
    }
}