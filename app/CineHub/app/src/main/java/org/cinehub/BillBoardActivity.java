package org.cinehub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.cinehub.utils.MovieAdapter;
import org.cinehub.utils.MovieTest;
import org.cinehub.utils.Movies;

import java.util.ArrayList;
import java.util.List;

public class BillBoardActivity extends AppCompatActivity {

    private MovieAdapter adapter;
    private RecyclerView rv_movies;
    private List<MovieTest> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_board);

        data = new ArrayList<>(Movies.getMovies());
        adapter = new MovieAdapter(data);
        RecyclerView rv_movies = findViewById(R.id.rvMovies);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_movies.setLayoutManager(layoutManager);
        rv_movies.setAdapter(adapter);
    }
}