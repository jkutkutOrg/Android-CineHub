package org.cinehub.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * SeatReservation model class.
 *
 * @author Jkutkut
 */
public class SeatReservation extends CinehubModel implements Parcelable {

    public static final String DB_REF = "seat_reservation";

    private int projection;
    private int row;
    private int col;
    private int reservation;

    public SeatReservation() {}

    public SeatReservation(int projection, int row, int col, int reservation) {
        setProjection(projection);
        setRow(row);
        setCol(col);
        setReservation(reservation);
    }

    protected SeatReservation(Parcel in) {
        this(
            in.readInt(),
            in.readInt(),
            in.readInt(),
            in.readInt()
        );
    }

    public static final Creator<SeatReservation> CREATOR = new Creator<SeatReservation>() {
        @Override
        public SeatReservation createFromParcel(Parcel in) {
            return new SeatReservation(in);
        }

        @Override
        public SeatReservation[] newArray(int size) {
            return new SeatReservation[size];
        }
    };

    // GETTERS
    @NonNull
    public static String getDBRef() {
        return DB_REF;
    }

    public int getProjection() {
        return projection;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getReservation() {
        return reservation;
    }

    // SETTERS
    public void setProjection(int projection) {
        this.projection = projection;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setReservation(int reservation) {
        this.reservation = reservation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(projection);
        dest.writeInt(row);
        dest.writeInt(col);
        dest.writeInt(reservation);
    }
}
