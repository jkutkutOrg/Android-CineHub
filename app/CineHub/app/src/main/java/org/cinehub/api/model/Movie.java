package org.cinehub.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * Movie model class.
 *
 * @author Jkutkut
 */
public class Movie implements Parcelable {

    private String name;
    private String description;
    private String banner;
    private float price;

    public Movie() {}

    public Movie(String name, String description, String banner, float price) {
        setName(name);
        setDescription(description);
        setBanner(banner);
        setPrice(price);
    }

    protected Movie(Parcel in) {
        this(
            in.readString(),
            in.readString(),
            in.readString(),
            in.readFloat()
        );
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return name.equals(movie.name);
    }

    // GETTERS
    @Override
    public String toString() {
        return String.format(
            Locale.getDefault(),
            "Movie{name=%s, description=%s, img=%s, price=%f}",
            name, description, banner, price
        );
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBanner() {
        return banner;
    }

    public float getPrice() {
        return price;
    }

    // SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0b0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(banner);
        dest.writeFloat(price);
    }
}
