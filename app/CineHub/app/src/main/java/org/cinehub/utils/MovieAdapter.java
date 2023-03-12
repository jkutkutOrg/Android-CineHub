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
import org.cinehub.api.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieVH> {

    private ArrayList<Movie> movieList;
    private OnMovieClickListener onMovieClickListener;

    public MovieAdapter(ArrayList<Movie> movieList, OnMovieClickListener onMovieClickListener) {
        this.movieList = movieList;
        this.onMovieClickListener = onMovieClickListener;
    }

    @NonNull
    @Override
    public MovieVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieVH holder, int position) {
        Movie movie = movieList.get(position);
        holder.tvTitle.setText(movie.getName());
        holder.tvDescription.setText(movie.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMovieClickListener.onMovieClicked(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public interface OnMovieClickListener {
        void onMovieClicked(Movie movie);
    }

    public static class MovieVH extends RecyclerView.ViewHolder {

        TextView tvTitle, tvReleaseDate, tvDescription;
        ImageView ivMovie;

        public MovieVH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvDescription = itemView.findViewById(R.id.tvMovieDescription);
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
            ivMovie = itemView.findViewById(R.id.ivMovieImage);
        }
    }
}
