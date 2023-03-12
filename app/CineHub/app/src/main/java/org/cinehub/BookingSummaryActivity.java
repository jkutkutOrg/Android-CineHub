package org.cinehub;

import android.os.Bundle;
import android.widget.TextView;

import org.cinehub.api.model.Movie;
import org.cinehub.api.model.SeatReservation;

public class BookingSummaryActivity extends NavActivity {

    public static final String EXTRA_SEAT_RESERVATION = "SeatReservation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_summary);

        TextView tvMovieName = findViewById(R.id.tvSummaryMovieName);
        TextView tvDate = findViewById(R.id.tvSummaryDate);
        TextView tvTime = findViewById(R.id.tvSummaryTime);
        TextView tvRoom = findViewById(R.id.tvSummaryRoom);
        TextView tvSeat = findViewById(R.id.tvSummarySeat);
        TextView tvPrice = findViewById(R.id.tvSummaryPrice);

        Movie movie = getIntent().getParcelableExtra(BillBoardActivity.EXTRA_MOVIE);
        SeatReservation seat = getIntent().getParcelableExtra(EXTRA_SEAT_RESERVATION);

        tvMovieName.setText(movie.getName());
        tvSeat.setText(getString(R.string.label_booking_seat_dat, seat.getRow(), seat.getCol()));

        findViewById(R.id.btnSummaryConfirmation).setOnClickListener(v -> onBookingConfirmation());
    }

    private void onBookingConfirmation() {
        // TODO send data to db
    }
}