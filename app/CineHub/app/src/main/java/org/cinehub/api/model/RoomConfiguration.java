package org.cinehub.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * RoomConfiguration model class.
 *
 * @author  Jkutkut
 */
public class RoomConfiguration implements Parcelable {
    public static final char FREE = 'f';
    public static final char OCCUPIED = 'o';
    public static final char GAP = ' ';

    private int room;
    private int row;
    private int col;
    private char state;

    public RoomConfiguration() {}

    public RoomConfiguration(int room, int row, int col, char state) {
        setRoom(room);
        setRow(row);
        setCol(col);
        setState(state);
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
    public int getRoom() {
        return room;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getState() {
        return state;
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

    public void setState(char state) {
        this.state = state; // TODO validate
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
        dest.writeInt((int) state);
    }
}
