package org.cinehub.javabeans;

public class MovieRoom {
    private final int id;
    private final int rows;
    private final int cols;
    private final RoomSeat[][] seatArrangement;

    public MovieRoom(int id, int rows, int cols, Character[][] seatArrangement) {
        this.id = id;
        this.rows = rows;
        this.cols = cols;
        this.seatArrangement = new RoomSeat[rows][cols];
        for (int i = 0; i < rows; i++) for (int j = 0; j < cols; j++)
            this.seatArrangement[i][j] = new RoomSeat(seatArrangement[i][j]);
    }

    public int getId() {
        return id;
    }

    public int getRowCount() {
        return rows;
    }

    public int getColCount() {
        return cols;
    }

    public RoomSeat getSeatAtPos(int row, int col) {
        return seatArrangement[row][col];
    }
}
