package org.cinehub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.cinehub.api.model.Movie;
import org.cinehub.utils.MovieAdapter;

import java.util.ArrayList;

public class BillBoardActivity extends AppCompatActivity {

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
    }
}
