package org.cinehub;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.CinehubDB;
import org.cinehub.api.model.Projection;
import org.cinehub.api.model.Seat;
import org.cinehub.enums.SeatType;

import java.util.ArrayList;

public class SeatSelectionActivity extends NavActivity {

    private final ArrayList<Seat> reservations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        CinehubDB api = CinehubAPI.getDBInstance();

        int roomId = ((Projection) getIntent()
                .getParcelableExtra(BillBoardActivity.EXTRA_PROJECTION))
                .getRoom();
        TextView tvRoomName = findViewById(R.id.tvRoomName);
        tvRoomName.setText(getString(R.string.label_room_name, roomId));

        api.getProjectionConfiguration(roomId, encodedLayout -> generateRoomDisplayLayout(
                findViewById(R.id.tblSeat),
                    new MovieRoom(encodedLayout.length, encodedLayout[0].length,
                            encodedLayout)),
                System.err::println);
        findViewById(R.id.fabSelectionAccept).setOnClickListener(this::onSelectionAccepted);
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
            seatPreview.addView(seatRow, 0);
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
            Seat res = new Seat(row, col);
            if (!reservations.contains(res)) {
                reservations.add(res);
                seatBtn.setCompoundDrawablesWithIntrinsicBounds(null, selIco, null,
                        null);
            } else {
                reservations.remove(res);
                seatBtn.setCompoundDrawablesWithIntrinsicBounds(null, stdIco, null,
                        null);
            }
            // TODO hide button when no seats are selected
        });
        seatBtn.setText(getString(R.string.label_seat_placement, row, col));
        seatBtn.setGravity(Gravity.CENTER);
        seatBtn.setTextColor(getResources().getColor(R.color.white));
        seatBtn.setCompoundDrawablesWithIntrinsicBounds(null, stdIco, null, null);
        return seatBtn;
    }

    private void onSelectionAccepted(View v) {
        if (reservations.isEmpty()) {
            Toast.makeText(this, "Please select at least one seat", Toast.LENGTH_SHORT).show();
            return;
        }
        advanceActivity(() -> new Intent(this, BookingSummaryActivity.class)
                    .putParcelableArrayListExtra(BookingSummaryActivity.EXTRA_SEAT_RESERVATIONS,
                            reservations));
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