package org.cinehub.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Locale;

/**
 * Reservation model class.
 *
 * @author Jkutkut
 */
public class Reservation implements Parcelable {

    private int user;

    public Reservation() {}

    public Reservation(int user) {
        setUser(user);
    }

    protected Reservation(Parcel in) {
        this(
            in.readInt()
        );
    }

    public static final Creator<Reservation> CREATOR = new Creator<Reservation>() {
        @Override
        public Reservation createFromParcel(Parcel in) {
            return new Reservation(in);
        }

        @Override
        public Reservation[] newArray(int size) {
            return new Reservation[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return user == that.user;
    }

    // GETTERS
    @Override
    public String toString() {
        return String.format(
            Locale.getDefault(),
            "Reservation{user=%d}",
            user
        );
    }

    public int getUser() {
        return user;
    }

    // SETTERS
    public void setUser(int user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(user);
    }
}
