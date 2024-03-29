package org.cinehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.CinehubAuth;
import org.cinehub.api.CinehubDB;
import org.cinehub.api.model.Movie;
import org.cinehub.api.model.Projection;
import org.cinehub.api.model.Seat;

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
        ImageView ivBanner = findViewById(R.id.ivBanner);
        Button btnConfirmation = findViewById(R.id.btnSummaryConfirmation);

        Movie movie = getIntent().getParcelableExtra(BillBoardActivity.EXTRA_MOVIE);
        Projection projection = getIntent().getParcelableExtra(BillBoardActivity.EXTRA_PROJECTION);
        ArrayList<Seat> reservations = getIntent()
                .getParcelableArrayListExtra(EXTRA_SEAT_RESERVATIONS);

        tvMovieName.setText(movie.getName());
        String timedate = projection.getTimedate();
        int timeIdx = timedate.indexOf('T') + 1;
        StringBuilder seatStr = new StringBuilder();
        for (Seat seat: reservations) {
            seatStr.append(getString(R.string.label_booking_seat_dat, seat.getRow(),
                    seat.getCol())).append("\n");
        }

        tvDate.setText(String.format("\n%s", timedate.substring(0, 10)));
        tvTime.setText(String.format("\n%s", timedate.substring(timeIdx, timeIdx + 8)));
        tvRoom.setText(String.format("\n%s", getString(R.string.label_room_name, projection.getRoom())));
        tvSeat.setText(seatStr.toString());
        btnConfirmation.setText(String.format(
            getString(R.string.btn_buy_now),
            getString(R.string.label_booking_price_dat, movie.getPrice() * reservations.size())
        ));

        Glide.with(this)
                .load(movie.getBanner())
                .centerCrop()
                .into(ivBanner);

        findViewById(R.id.btnSummaryConfirmation).setOnClickListener(v -> {
            CinehubDB db = CinehubAPI.getDBInstance();
            CinehubAuth auth = CinehubAPI.getAuthInstance();
            auth.whoami(user -> db.addReservation(
                    user, projection, new ArrayList<>(reservations),
                    () -> {
                        startActivity(new Intent(this, EndActivity.class));
                        finish();
                    },
                    System.err::println),
                    System.err::println);
        });
    }
}