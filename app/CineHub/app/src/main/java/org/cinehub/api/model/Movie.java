package org.cinehub.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Movie model class.
 *
 * @author Jkutkut
 */
public class Movie extends CinehubModel implements Parcelable {
    public static final String DB_REF = "movie";

    private String name;
    private String description;
    private String img;
    private float price;

    public Movie() {}

    public Movie(String name, String description, String img, float price) {
        setName(name);
        setDescription(description);
        setImg(img);
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

    // GETTERS
    @NonNull
    public static String getDBRef() {
        return DB_REF;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImg() {
        return img;
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

    public void setImg(String img) {
        this.img = img;
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
        dest.writeString(img);
        dest.writeFloat(price);
    }
}
