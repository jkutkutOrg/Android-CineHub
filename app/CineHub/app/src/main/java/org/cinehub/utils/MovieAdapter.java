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
        // holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return 0;
        // Return the number of movies API
    }


    public static class MovieVH extends RecyclerView.ViewHolder {

        TextView tvTitle, tvReleaseDate, tvDescription;
        ImageView ivMovie;

        public MovieVH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvDescription = itemView.findViewById(R.id.tvMovieDescription);
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);

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