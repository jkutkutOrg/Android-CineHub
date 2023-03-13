package org.cinehub.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Generic class to describe a seat.
 *
 * @author Jkutkut
 */
public class Seat implements Parcelable {
    public static final char FREE = 'f';
    public static final char OCCUPIED = 'o';
    public static final char GAP = ' ';
    public static final char[] STATES = {FREE, OCCUPIED, GAP};

    private int row;
    private int col;

    public Seat() {}

    public Seat(int row, int col) {
        this.row = row;
        this.col = col;
    }

    private Seat(Parcel in) {
        row = in.readInt();
        col = in.readInt();
    }

    public static final Creator<Seat> CREATOR = new Creator<Seat>() {
        @Override
        public Seat createFromParcel(Parcel in) {
            return new Seat(in);
        }

        @Override
        public Seat[] newArray(int size) {
            return new Seat[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return row == seat.row && col == seat.col;
    }

    // GETTERS
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    // SETTERS
    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public int describeContents() {
        return 0b0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(row);
        dest.writeInt(col);
    }
}
