package org.cinehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.model.Movie;
import org.cinehub.api.model.Projection;
import org.cinehub.api.model.SeatReservation;
import org.cinehub.api.model.User;

import java.util.ArrayList;

public class BookingSummaryActivity extends NavActivity {

    public static final String EXTRA_SEAT_RESERVATIONS = "SeatReservations";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_summary);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
        User user = getIntent().getParcelableExtra(LoginActivity.EXTRA_USER);

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
        findViewById(R.id.btnSummaryConfirmation).setOnClickListener(v -> {
            CinehubAPI api = new CinehubAPI();
            api.addReservation(user, projection, new ArrayList<>(reservations), () -> {
                Toast.makeText(this, "Data was sent scucessfully!", Toast.LENGTH_SHORT).show();
                advanceActivity(() -> new Intent(this, BillBoardActivity.class));
                finish();
            }, System.err::println);
        });
    }
}