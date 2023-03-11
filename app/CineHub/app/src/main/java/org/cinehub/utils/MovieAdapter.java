package org.cinehub.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.cinehub.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieVH> {

    public MovieAdapter() {
        // TODO: Initialize the adapter with the data
    }

    @NonNull
    @Override
    public MovieVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieVH holder, int position) {
        // Get the movie at the current position API
    }

    @Override
    public int getItemCount() {
        return 0;
        // Return the number of movies API
    }


    public static class MovieVH extends RecyclerView.ViewHolder {

        TextView tv_title, tv_release_date, tv_description;
        ImageView iv_movie;

        public MovieVH(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tvMovieTitle);
            tv_description = itemView.findViewById(R.id.tvMovieDescription);
            tv_release_date = itemView.findViewById(R.id.tvReleaseDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: Go to seat selection
                    Toast.makeText(itemView.getContext(), "Movie clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }

        /*
        public void bind(MovieTest movie) {
            tv_title.setText(movie.getTitle());
            tv_release_date.setText(movie.getReleaseDate());
            tv_description.setText(movie.getDescription());
        } */
    }
}