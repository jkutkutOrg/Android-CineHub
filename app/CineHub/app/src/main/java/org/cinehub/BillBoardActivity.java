package org.cinehub;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import org.cinehub.api.model.Movie;
import org.cinehub.api.model.User;
import org.cinehub.utils.MovieAdapter;

import java.util.ArrayList;

public class BillBoardActivity extends NavActivity {

    public static final String EXTRA_MOVIE = "Movie";

    private MovieAdapter mvAdapter;
    private RecyclerView rvMovies;
    private ArrayList<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_board);

        movieList = new ArrayList<>();
        movieList.add(new Movie("Movie 1", "Description 1", "img1", 10.0f));
        movieList.add(new Movie("Movie 2", "Description 2", "img2", 12.0f));
        movieList.add(new Movie("Movie 3", "Description 3", "img3", 8.0f));

        rvMovies = findViewById(R.id.rvMovies);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvMovies.setLayoutManager(layoutManager);

        mvAdapter = new MovieAdapter(movieList);
        rvMovies.setAdapter(mvAdapter);

        // TODO implement once Rv listeners are done
//        advanceActivity(() -> new Intent(this, SeatSelectionActivity.class)
//                .putExtra(EXTRA_MOVIE, new Movie()));
    }


    @NonNull
    @Override
    protected Intent collectData(@NonNull Intent intent) {
        return intent
                .putExtra(LoginActivity.EXTRA_USER, (User) getIntent()
                        .getParcelableExtra(LoginActivity.EXTRA_USER));
    }
}
