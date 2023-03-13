package org.cinehub.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Locale;

/**
 * RoomConfiguration model class.
 *
 * @author  Jkutkut
 */
public class SpecialSeat extends Seat implements Parcelable {
    private static final int INVALID = -1;

    private int room;
    private char type;

    public SpecialSeat() {
        super();
    }

    public SpecialSeat(int room, int row, int col, char type) {
        super(row, col);
        setRoom(room);
        setTypeChar(type);
    }

    private SpecialSeat(Parcel in) {
        this(
            in.readInt(),
            in.readInt(),
            in.readInt(),
            (char) in.readInt()
        );
    }

    public static final Creator<SpecialSeat> CREATOR = new Creator<SpecialSeat>() {
        @Override
        public SpecialSeat createFromParcel(Parcel in) {
            return new SpecialSeat(in);
        }

        @Override
        public SpecialSeat[] newArray(int size) {
            return new SpecialSeat[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecialSeat that = (SpecialSeat) o;
        return getRoom() == that.getRoom() &&
            getRow() == that.getRow() &&
            getCol() == that.getCol();
    }

    // GETTERS
    @NonNull
    @Override
    public String toString() {
        return String.format(
            Locale.getDefault(),
            "RoomConfiguration{room=%d, row=%d, col=%d, state=%c}",
            getRoom(), getRow(), getCol(), getType()
        );
    }

    public int getRoom() {
        return room;
    }

    public char getType() {
        return type;
    }

    // SETTERS
    public void setRoom(int room) {
        this.room = room;
    }

    public void setType(String typeStr) {
        // Firebase does not support char
        setTypeChar(typeStr.charAt(0));
    }

    public void setTypeChar(char type) {
        for (char s : STATES) {
            if (s == type) {
                this.type = type;
                return;
            }
        }
        throw new IllegalArgumentException("Invalid state");
    }

    @Override
    public int describeContents() {
        return 0b0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(getRoom());
        dest.writeInt(getRow());
        dest.writeInt(getCol());
        dest.writeInt(getType());
    }
}
