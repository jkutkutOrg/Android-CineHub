package org.cinehub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.cinehub.utils.MovieAdapter;

public class BillBoardActivity extends AppCompatActivity {

    private MovieAdapter adapter;
    private RecyclerView rv_movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_board);

        RecyclerView rv_movies = findViewById(R.id.rvMovies);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_movies.setLayoutManager(layoutManager);
        rv_movies.setAdapter(adapter);
    }
}