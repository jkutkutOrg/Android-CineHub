package org.cinehub.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.cinehub.R;
import org.cinehub.api.model.Movie;
import org.cinehub.api.model.Projection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.TimeZone;

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
        holder.tvRoom.setText("Lounge: R" + projection.getRoom()); // TODO: Chang to string resource
        try {
            holder.tvTimedate.setText(dateToISO8601(projection.getTimedate()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Glide.with(holder.itemView.getContext())
                .load(movie.getBanner())
                .placeholder(R.drawable.ic_logo_extended)
                .error(R.drawable.ic_launcher_background)
                .into(holder.ivMovie);

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


    public String dateToISO8601(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date newDate = dateFormat.parse(date);

        SimpleDateFormat dateOut = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return dateOut.format(newDate);
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
