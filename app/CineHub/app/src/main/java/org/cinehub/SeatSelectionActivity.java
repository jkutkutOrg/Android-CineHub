package org.cinehub;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import org.cinehub.enums.SeatType;
import org.cinehub.javabeans.MovieRoom;

public class SeatSelectionActivity extends AppCompatActivity {

    public static final String EXTRA_LAYOUT = "Layout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        Character[][] encodedLayout =
                (Character[][]) getIntent().getSerializableExtra(EXTRA_LAYOUT);
        generateRoomDisplayLayout(findViewById(R.id.seatPreviewTableLayout),
                new MovieRoom(encodedLayout.length, encodedLayout[0].length, encodedLayout));
    }

    private void generateRoomDisplayLayout(TableLayout seatPreview, MovieRoom room) {
        TableRow seatRow;
        for (int row = 0; row < room.getRowCount(); row++) {
            seatRow = new TableRow(this);
            seatRow.setGravity(Gravity.CENTER);
            for (int col = 0; col < room.getColCount(); col++) {
                SeatType seatType = room.getSeatAtPos(row, col).getSeatType();
                ImageView seatBtn;
                if (seatType.equals(SeatType.NONEXISTENT))
                    seatBtn = new ImageView(this);
                else
                    seatBtn = new ImageButton(this);
                seatBtn.setImageDrawable(AppCompatResources
                        .getDrawable(this, seatType.getResourceId()));
                seatRow.addView(seatBtn);
            }
            seatPreview.addView(seatRow);
        }
    }
}