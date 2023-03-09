package org.cinehub.javabeans;

import org.cinehub.enums.SeatType;

public class RoomSeat {
    private final SeatType seatType;

    public RoomSeat(char encoded_seat_type) {
        switch (encoded_seat_type) {
            case 'f':
                seatType = SeatType.EMPTY;
                break;
            case 'o':
                seatType = SeatType.OCCUPIED;
                break;
            case ' ':
                seatType = SeatType.NONEXISTENT;
                break;
            default:
                throw new IllegalArgumentException("Unrecognized encoded seat type while initializing seat");
        }
    }

    public SeatType getSeatType() {
        return seatType;
    }
}
