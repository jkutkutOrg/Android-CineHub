package org.cinehub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.CinehubDB;
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
    private CinehubDB db = CinehubAPI.getDBInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_board);

        projectionMovieMap = new LinkedHashMap<>();
        db.getProjections(projections -> {
            AtomicInteger i = new AtomicInteger();
            for (Projection projection : projections) {
                db.getMovie(projection.getMovie(), movie -> {
                    projectionMovieMap.put(projection, movie);
                    mvAdapter.notifyItemChanged(i.getAndIncrement());
                }, System.err::println);
            }
        }, System.err::println);

        rvMovies = findViewById(R.id.rvMovies);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvMovies.setLayoutManager(layoutManager);

        mvAdapter = new MovieAdapter(projectionMovieMap, this);
        rvMovies.setAdapter(mvAdapter);
    }

    @Override
    public void onProjectionClicked(Projection projection) {
        Movie movie = projectionMovieMap.get(projection);
        Toast.makeText(this, "Movie clicked: " + movie.getName(), Toast.LENGTH_SHORT).show();
        advanceActivity(() -> new Intent(this, SeatSelectionActivity.class)
                .putExtra(EXTRA_MOVIE, movie)
                .putExtra(EXTRA_PROJECTION, projection));
    }
}
