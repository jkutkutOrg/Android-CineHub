package org.cinehub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.CinehubDB;
import org.cinehub.api.CinehubStorage;
import org.cinehub.api.model.Movie;
import org.cinehub.api.model.Projection;
import org.cinehub.utils.MovieAdapter;

import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BillBoardActivity extends NavActivity implements MovieAdapter.OnMovieClickListener {

    public static final String EXTRA_MOVIE = "Movie";
    public static final String EXTRA_PROJECTION = "Projection";

    private MovieAdapter mvAdapter;
    private RecyclerView rvMovies;
    private LinkedHashMap<Projection, Movie> projectionMovieMap;
    private CinehubDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_board);
        db = CinehubAPI.getDBInstance();

        projectionMovieMap = new LinkedHashMap<>();

        rvMovies = findViewById(R.id.rvMovies);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvMovies.setLayoutManager(layoutManager);

        mvAdapter = new MovieAdapter(projectionMovieMap, this);
        rvMovies.setAdapter(mvAdapter);

        db.getProjections(
            projections -> {
                db.getMoviesWithBanner(
                    movies -> {
                        for (Projection p : projections) {
                            projectionMovieMap.put(p, movies.get(p.getMovie()));
                        }
                        mvAdapter.notifyDataSetChanged();
                    },
                    System.err::println
                );
            },
            System.err::println
        );
    }

    @Override
    public void onProjectionClicked(Projection projection) {
        Movie movie = projectionMovieMap.get(projection);
        advanceActivity(() -> new Intent(this, SeatSelectionActivity.class)
                .putExtra(EXTRA_MOVIE, movie)
                .putExtra(EXTRA_PROJECTION, projection));
    }

}
