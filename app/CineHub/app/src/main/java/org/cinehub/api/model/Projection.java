package org.cinehub.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Projection model class.
 *
 * @author Jkutkut
 */
public class Projection implements Parcelable {
    private int room;
    private int movie;
    private String timedate;

    public Projection() {}

    public Projection(int room, int movie, String timedate) {
        setRoom(room);
        setMovie(movie);
        setTimedate(timedate);
    }

    protected Projection(Parcel in) {
        this(
            in.readInt(),
            in.readInt(),
            in.readString()
        );
    }

    public static final Creator<Projection> CREATOR = new Creator<Projection>() {
        @Override
        public Projection createFromParcel(Parcel in) {
            return new Projection(in);
        }

        @Override
        public Projection[] newArray(int size) {
            return new Projection[size];
        }
    };

    // GETTERS
    public int getRoom() {
        return room;
    }

    public int getMovie() {
        return movie;
    }

    public String getTimedate() {
        return timedate;
    }

    // SETTERS
    public void setRoom(int room) {
        this.room = room;
    }

    public void setMovie(int movie) {
        this.movie = movie;
    }

    public void setTimedate(String timedate) {
        this.timedate = timedate;
    }

    @Override
    public int describeContents() {
        return 0b0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(room);
        dest.writeInt(movie);
        dest.writeString(timedate);
    }
}
