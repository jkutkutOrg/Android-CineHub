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
public class RoomConfiguration implements Parcelable {

    public static final char FREE = 'f';
    public static final char OCCUPIED = 'o';
    public static final char GAP = ' ';
    public static final char[] STATES = {FREE, OCCUPIED, GAP};

    private int room;
    private int row;
    private int col;
    private char type;

    public RoomConfiguration() {}

    public RoomConfiguration(int room, int row, int col, char type) {
        setRoom(room);
        setRow(row);
        setCol(col);
        setTypeChar(type);
    }

    protected RoomConfiguration(Parcel in) {
        this(
            in.readInt(),
            in.readInt(),
            in.readInt(),
            (char) in.readInt()
        );
    }

    public static final Creator<RoomConfiguration> CREATOR = new Creator<RoomConfiguration>() {
        @Override
        public RoomConfiguration createFromParcel(Parcel in) {
            return new RoomConfiguration(in);
        }

        @Override
        public RoomConfiguration[] newArray(int size) {
            return new RoomConfiguration[size];
        }
    };

    // GETTERS
    @Override
    public String toString() {
        return String.format(
            Locale.getDefault(),
            "RoomConfiguration{room=%d, row=%d, col=%d, state=%c}",
            room, row, col, type
        );
    }

    public int getRoom() {
        return room;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getType() {
        return type;
    }

    // SETTERS
    public void setRoom(int room) {
        this.room = room;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
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
        dest.writeInt(room);
        dest.writeInt(row);
        dest.writeInt(col);
        dest.writeInt((int) type);
    }
}
