package org.cinehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.CinehubDB;
import org.cinehub.api.model.Movie;
import org.cinehub.utils.MovieAdapter;

import java.util.ArrayList;

public class BillBoardActivity extends NavActivity implements MovieAdapter.OnMovieClickListener {

    public static final String EXTRA_MOVIE = "Movie";

    private MovieAdapter mvAdapter;
    private RecyclerView rvMovies;
    private ArrayList<Movie> movieList;
    private CinehubDB db = CinehubAPI.getDBInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_board);

        movieList = new ArrayList<>();
        db.getMovies(
                movies -> {
                    for (Movie movie : movies) {
                        movieList.add(new Movie(
                                movie.getName(),
                                movie.getDescription(),
                                movie.getImg(),
                                movie.getPrice()
                        ));
                    }
                    mvAdapter.notifyDataSetChanged();
                },
                (error) -> {
                    System.out.println("Error: " + error);
                }
        );

        rvMovies = findViewById(R.id.rvMovies);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvMovies.setLayoutManager(layoutManager);

        mvAdapter = new MovieAdapter(movieList, this);
        rvMovies.setAdapter(mvAdapter);
    }

    @Override
    public void onMovieClicked(Movie movie) {
        // TODO: Go to seat selection activity
        Toast.makeText(this, "Movie clicked: " + movie.getName(), Toast.LENGTH_SHORT).show();
        advanceActivity(() -> new Intent(this, SeatSelectionActivity.class)
                .putExtra(EXTRA_MOVIE, new Movie()));
    }
}
