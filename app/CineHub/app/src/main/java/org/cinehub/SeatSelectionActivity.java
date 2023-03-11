package org.cinehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import org.cinehub.enums.SeatType;
import org.cinehub.javabeans.MovieRoom;

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
                new MovieRoom(roomId, encodedLayout.length, encodedLayout[0].length,
                        encodedLayout));
    }

    private void generateRoomDisplayLayout(TableLayout seatPreview, MovieRoom room) {
        TableRow seatRow;
        for (int row = 0; row < room.getRowCount(); row++) {
            seatRow = new TableRow(this);
            seatRow.setGravity(Gravity.CENTER);
            for (int col = 0; col < room.getColCount(); col++) {
                SeatType seatType = room.getSeatAtPos(row, col).getSeatType();
                ImageButton seatBtn = new ImageButton(this);
                // Disable button if seat is occupied or nonexistent
                if (seatType.equals(SeatType.NONEXISTENT) || seatType.equals(SeatType.OCCUPIED))
                    seatBtn.setEnabled(false);
                seatBtn.setBackgroundColor(0x0); // FIXME this should be in styles.xml
                // required for lambda expression to compile
                int currRow = row, currCol = col;
                seatBtn.setOnClickListener(v -> onSeatSelected(currRow, currCol));
                seatBtn.setImageDrawable(AppCompatResources
                        .getDrawable(this, seatType.getResourceId()));
                seatRow.addView(seatBtn);
            }
            seatPreview.addView(seatRow);
        }
    }

    private void onSeatSelected(int row, int col) {
        Intent i = new Intent(this, BookingSummaryActivity.class);
        startActivity(i);
    }
}