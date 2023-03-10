package org.cinehub;

import android.os.Bundle;
import android.widget.TextView;

import org.cinehub.api.model.Movie;
import org.cinehub.api.model.Projection;
import org.cinehub.api.model.SeatReservation;

import java.util.ArrayList;

public class BookingSummaryActivity extends NavActivity {

    public static final String EXTRA_SEAT_RESERVATIONS = "SeatReservations";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_summary);

        TextView tvMovieName = findViewById(R.id.tvSummaryMovieName);
        TextView tvDate = findViewById(R.id.tvSummaryDate);
        TextView tvTime = findViewById(R.id.tvSummaryTime);
        TextView tvRoom = findViewById(R.id.tvSummaryRoom);
        TextView tvSeat = findViewById(R.id.tvSummarySeat);
        TextView tvSeatLbl = findViewById(R.id.tvSummarySeatLbl);
        TextView tvPrice = findViewById(R.id.tvSummaryPrice);

        Movie movie = getIntent().getParcelableExtra(BillBoardActivity.EXTRA_MOVIE);
        Projection projection = getIntent().getParcelableExtra(BillBoardActivity.EXTRA_PROJECTION);
        ArrayList<SeatReservation> reservations = getIntent()
                .getParcelableArrayListExtra(EXTRA_SEAT_RESERVATIONS);

        tvMovieName.setText(movie.getName());
        String timedate = projection.getTimedate();
        int timeIdx = timedate.indexOf('T') + 1;
        StringBuilder seatStr = new StringBuilder();
        for (SeatReservation seat: reservations) {
            seatStr.append(getString(R.string.label_booking_seat_dat, seat.getRow(),
                    seat.getCol())).append("\n");
        }

        tvDate.setText(timedate.substring(0, 10));
        tvTime.setText(timedate.substring(timeIdx, timeIdx + 8));
        tvRoom.setText(getString(R.string.label_room_name, projection.getRoom()));
        tvSeatLbl.setText(getString(R.string.label_booking_seat, reservations.size()));
        tvSeat.setText(seatStr.toString());
        tvPrice.setText(getString(R.string.label_booking_price_dat, movie
                .getPrice() * reservations.size()));
        findViewById(R.id.btnSummaryConfirmation).setOnClickListener(v -> onBookingConfirmation());
    }

    private void onBookingConfirmation() {
        // TODO send data to db
    }
}