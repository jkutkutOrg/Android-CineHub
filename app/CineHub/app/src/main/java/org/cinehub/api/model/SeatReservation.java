package org.cinehub.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Locale;

/**
 * SeatReservation model class.
 *
 * @author Jkutkut
 */
public class SeatReservation extends Seat implements Parcelable {

    private int projection;
    private int reservation;

    public SeatReservation() {}

    public SeatReservation(int row, int col) {
        this(-1, row, col, -1);
    }

    public SeatReservation(int projection, int row, int col, int reservation) {
        super(row, col);
        setProjection(projection);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatReservation that = (SeatReservation) o;
        return getProjection() == that.getProjection() &&
            getRow() == that.getRow() &&
            getCol() == that.getCol() &&
            getReservation() == that.getReservation();
    }

    // GETTERS
    @Override
    public String toString() {
        return String.format(
            Locale.getDefault(),
            "SeatReservation{projection=%d, row=%d, col=%d, reservation=%d}",
            getProjection(), getRow(), getCol(), getReservation()
        );
    }

    public int getProjection() {
        return projection;
    }

    public int getReservation() {
        return reservation;
    }

    // SETTERS
    public void setProjection(int projection) {
        this.projection = projection;
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
        dest.writeInt(getProjection());
        dest.writeInt(getRow());
        dest.writeInt(getCol());
        dest.writeInt(getReservation());
    }

}
