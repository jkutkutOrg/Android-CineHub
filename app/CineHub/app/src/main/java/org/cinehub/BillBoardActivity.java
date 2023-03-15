package org.cinehub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.CinehubAuth;
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
    private CinehubAuth auth;
    private TextView tvStatusName;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_board);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        db = CinehubAPI.getDBInstance();
        auth = CinehubAPI.getAuthInstance();

        projectionMovieMap = new LinkedHashMap<>();

        rvMovies = findViewById(R.id.rvMovies);
        tvStatusName = findViewById(R.id.tvStatusName);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvMovies.setLayoutManager(layoutManager);

        mvAdapter = new MovieAdapter(projectionMovieMap, this);
        rvMovies.setAdapter(mvAdapter);

        auth.whoami(
                user -> {
                    if (user != null) {
                        tvStatusName.setText("Hello, " + user.getName() + "!");
                    } else {
                        tvStatusName.setText("Hello, Guest!");
                    }
                },
                (error) -> {
                    System.out.println("Error at me: " + error);
                }
        );

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
