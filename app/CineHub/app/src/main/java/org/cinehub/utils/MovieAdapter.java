package org.cinehub.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.cinehub.R;
import org.cinehub.api.model.Movie;
import org.cinehub.api.model.Projection;

import java.util.LinkedHashMap;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieVH> {

    private LinkedHashMap<Projection, Movie> projectionMovieMap;
    private OnMovieClickListener onMovieClickListener;

    public MovieAdapter(LinkedHashMap<Projection, Movie> projectionMovieMap, OnMovieClickListener onMovieClickListener) {
        this.projectionMovieMap = projectionMovieMap;
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
        Movie movie = (Movie) projectionMovieMap.values().toArray()[position];
        Projection projection = (Projection) projectionMovieMap.keySet().toArray()[position];
        holder.tvTitle.setText(movie.getName());
        holder.tvDescription.setText(movie.getDescription());
        holder.tvRoom.setText(String.valueOf(projection.getRoom()));
        holder.tvTimedate.setText(projection.getTimedate());
        holder.itemView.setOnClickListener(view ->
                onMovieClickListener.onProjectionClicked(projection));
    }

    @Override
    public int getItemCount() {
        return projectionMovieMap.size();
    }

    public interface OnMovieClickListener {
        void onProjectionClicked(Projection projection);
    }

    public static class MovieVH extends RecyclerView.ViewHolder {

        TextView tvTitle, tvReleaseDate, tvDescription, tvRoom, tvTimedate;
        ImageView ivMovie;

        public MovieVH(@NonNull View itemView) {
            super(itemView);
            tvRoom = itemView.findViewById(R.id.tvRcRoom);
            tvTimedate = itemView.findViewById(R.id.tvRcTimedate);
            tvTitle = itemView.findViewById(R.id.tvRcMovieTitle);
            tvDescription = itemView.findViewById(R.id.tvRcMovieDescription);
            tvReleaseDate = itemView.findViewById(R.id.tvRcReleaseDate);
            ivMovie = itemView.findViewById(R.id.ivRcMovieImage);
        }
    }
}
