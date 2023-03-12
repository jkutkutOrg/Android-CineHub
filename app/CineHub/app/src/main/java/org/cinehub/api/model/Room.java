package org.cinehub.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Locale;

/**
 * Room model class.
 *
 * @author Jkutkut
 */
public class Room implements Parcelable {

    private String name;
    private int rows;
    private int cols;

    public Room() {}

    public Room(String name, int rows, int cols) {
        setName(name);
        setRows(rows);
        setCols(cols);
    }

    protected Room(Parcel in) {
        this(
            in.readString(),
            in.readInt(),
            in.readInt()
        );
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    // GETTERS
    @Override
    public String toString() {
        return String.format(
            Locale.getDefault(),
            "Room { name: %s, rows: %d, cols: %d }",
            name, rows, cols
        );
    }

    public String getName() {
        return name;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    // SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    @Override
    public int describeContents() {
        return 0b0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(rows);
        dest.writeInt(cols);
    }
}
