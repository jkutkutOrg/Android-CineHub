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
    private int rows;
    private int cols;

    public Room() {}

    public Room(int rows, int cols) {
        setRows(rows);
        setCols(cols);
    }

    protected Room(Parcel in) {
        this(
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
    @NonNull
    @Override
    public String toString() {
        return String.format(
            Locale.getDefault(),
            "Room {rows: %d, cols: %d }",
            rows, cols
        );
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    // SETTERS
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
        dest.writeInt(rows);
        dest.writeInt(cols);
    }
}
