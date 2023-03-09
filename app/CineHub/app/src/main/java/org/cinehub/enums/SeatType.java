package org.cinehub.enums;

import org.cinehub.R;

public enum SeatType {
    NONEXISTENT(R.drawable.ic_nonexistent_seat),
    EMPTY(R.drawable.ic_empty_seat),
    OCCUPIED(R.drawable.ic_occupied_seat);

    private final int drawable;
    SeatType(int typeResource) {
        this.drawable = typeResource;
    }

    public int getResourceId() {
        return drawable;
    }
}
