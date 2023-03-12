package org.cinehub;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.model.SeatReservation;
import org.cinehub.enums.SeatType;

import java.util.ArrayList;

public class SeatSelectionActivity extends NavActivity {

    public static final String EXTRA_ROOM_ID = "RoomId";

    private final ArrayList<SeatReservation> reservations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        CinehubAPI api = new CinehubAPI();

        int roomId = getIntent().getIntExtra(EXTRA_ROOM_ID, -1);
        TextView tvRoomName = findViewById(R.id.tvRoomName);
        tvRoomName.setText(getString(R.string.label_room_name, String.valueOf(roomId)));

        api.getProjectionConfiguration(roomId, encodedLayout -> generateRoomDisplayLayout(
                findViewById(R.id.tblSeat),
                    new MovieRoom(encodedLayout.length, encodedLayout[0].length,
                            encodedLayout)),
                System.err::println);
    }

    private void generateRoomDisplayLayout(TableLayout seatPreview, MovieRoom room) {
        TableRow seatRow;
        for (int row = 0; row < room.getRowCount(); row++) {
            seatRow = new TableRow(this);
            seatRow.setGravity(Gravity.CENTER);
            for (int col = 0; col < room.getColCount(); col++) {
                seatRow.addView(generateSeatButton(room.getSeatAtPos(row, col)
                        .getSeatType(), row, col));
            }
            seatPreview.addView(seatRow);
        }
    }

    @NonNull
    private Button generateSeatButton(SeatType seatType, int row, int col) {
        Button seatBtn = new Button(this);
        Drawable stdIco = AppCompatResources.getDrawable(this, seatType.getResourceId());
        Drawable selIco = AppCompatResources.getDrawable(this, R.drawable.ic_select_seat);
        // Disable button if seat is occupied or nonexistent
        if (seatType.equals(SeatType.NONEXISTENT) || seatType.equals(SeatType.OCCUPIED))
            seatBtn.setEnabled(false);
        seatBtn.setBackgroundColor(0x0);
        seatBtn.setOnClickListener(v -> {
            SeatReservation res = new SeatReservation(row, col);
            if (!reservations.contains(res)) {
                reservations.add(res);
                seatBtn.setCompoundDrawablesWithIntrinsicBounds(null, selIco, null,
                        null);
            } else {
                reservations.remove(res);
                seatBtn.setCompoundDrawablesWithIntrinsicBounds(null, stdIco, null,
                        null);
            }
        });
        seatBtn.setText(getString(R.string.label_seat_placement, row, col));
        seatBtn.setCompoundDrawablesWithIntrinsicBounds(null, stdIco, null, null);
        return seatBtn;
    }

    private static class MovieRoom {
        private final int rows;
        private final int cols;
        private final RoomSeat[][] seatArrangement;

        public MovieRoom(int rows, int cols, char[][] seatArrangement) {
            this.rows = rows;
            this.cols = cols;
            this.seatArrangement = new RoomSeat[rows][cols];
            for (int i = 0; i < rows; i++) for (int j = 0; j < cols; j++)
                this.seatArrangement[i][j] = new RoomSeat(seatArrangement[i][j]);
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

        private static class RoomSeat {
            private final SeatType seatType;

            public RoomSeat(char encodedSeatType) {
                switch (encodedSeatType) {
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
    }
}