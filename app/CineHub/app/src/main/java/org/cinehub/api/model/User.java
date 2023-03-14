package org.cinehub.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Locale;

/**
 * User model class.
 *
 * @author Jkutkut
 */
public class User implements Parcelable {

    private String email;
    private String name;

    public User() {}

    public User(String email) {
        this(email, null);
    }

    public User(String email, String name) {
        setEmail(email);
        setName(name);
    }

    protected User(Parcel in) {
        this(
            in.readString(),
            in.readString()
        );
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return email.equals(((User) o).email);
    }

    // GETTERS
    @Override
    public String toString() {
        return String.format(
            Locale.getDefault(),
            "User{email=%s, name=%s}",
            email, name
        );
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    // SETTERS
    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0b0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(name);
    }
}
