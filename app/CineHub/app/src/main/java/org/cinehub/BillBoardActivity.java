package org.cinehub;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.CinehubDB;
import org.cinehub.api.model.Movie;
import org.cinehub.utils.MovieAdapter;

import java.util.ArrayList;

public class BillBoardActivity extends AppCompatActivity {

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
                },
                (error) -> {
                    System.out.println("Error: " + error);
                }
        );

        rvMovies = findViewById(R.id.rvMovies);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvMovies.setLayoutManager(layoutManager);

        mvAdapter = new MovieAdapter(movieList);
        rvMovies.setAdapter(mvAdapter);
    }
}
