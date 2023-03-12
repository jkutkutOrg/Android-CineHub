package org.cinehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import org.cinehub.enums.SeatType;

public class SeatSelectionActivity extends AppCompatActivity {

    public static final String EXTRA_ROOM_ID = "RoomId";
    public static final String EXTRA_LAYOUT = "Layout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        Character[][] encodedLayout =
                (Character[][]) getIntent().getSerializableExtra(EXTRA_LAYOUT);
        int roomId = getIntent().getIntExtra(EXTRA_ROOM_ID, -1);
        TextView tvRoomName = findViewById(R.id.tvRoomName);
        tvRoomName.setText(getString(R.string.label_room_name, String.valueOf(roomId)));
        generateRoomDisplayLayout(findViewById(R.id.tblSeat),
                new MovieRoom(encodedLayout.length, encodedLayout[0].length,
                        encodedLayout));
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
        // Disable button if seat is occupied or nonexistent
        if (seatType.equals(SeatType.NONEXISTENT) || seatType.equals(SeatType.OCCUPIED))
            seatBtn.setEnabled(false);
        seatBtn.setBackgroundColor(0x0);
        // required for lambda expression to compile
        seatBtn.setOnClickListener(v -> onSeatSelected(row, col));
        seatBtn.setText(getString(R.string.label_seat_placement, row, col));
        seatBtn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources
                .getDrawable(this, seatType.getResourceId()), null, null);
        return seatBtn;
    }

    private void onSeatSelected(int row, int col) {
        Intent i = new Intent(this, BookingSummaryActivity.class);
        startActivity(i);
    }

    private static class MovieRoom {
        private final int rows;
        private final int cols;
        private final RoomSeat[][] seatArrangement;

        public MovieRoom(int rows, int cols, Character[][] seatArrangement) {
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