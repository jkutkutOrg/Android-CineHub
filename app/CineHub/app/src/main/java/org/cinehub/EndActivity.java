package org.cinehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.CinehubDB;
import org.cinehub.api.model.SeatReservation;

public class EndActivity extends NavActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        int reservationId = getIntent().getIntExtra("reservationId", -1);
        if (reservationId != -1) {
            updateUI(reservationId);
        }

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.btnEndReturn).setOnClickListener(v -> {
            advanceActivity(() ->
                    new Intent(this, HomeActivity.class).setFlags(
                            Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        });
    }

    private void updateUI(int reservationId) {
        TextView tvMovieName = findViewById(R.id.tvMovieName);
        TextView tvDate = findViewById(R.id.tvDate);
        TextView tvTime = findViewById(R.id.tvTime);
        TextView tvRoom = findViewById(R.id.tvRoom);
        TextView tvSeats = findViewById(R.id.tvSeats);

        CinehubDB db = CinehubAPI.getDBInstance();
        db.getSeatReservationReservation(
                reservationId,
                seats -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Seats: \n");
                    for (SeatReservation sr: seats) {
                        sb.append(String.format(
                                getString(R.string.label_seat_placement_detail) + "\n",
                                sr.getRow(), sr.getCol()
                        ));
                    }
                    tvSeats.setText(sb.toString());

                    db.getProjection(
                        seats.get(0).getProjection(),
                        projection -> {
                            db.getMovie(
                                projection.getMovie(),
                                movie -> {
                                    tvMovieName.setText(movie.getName());
                                },
                                System.err::println
                            );
                            tvRoom.setText(String.valueOf(projection.getRoom()));
                            String[] time = projection.getTimedate().split("T");
                            tvDate.setText(time[0]);
                            tvTime.setText(time[1].substring(0, 5));
                        }, System.err::println
                    );
                },
                System.err::println
        );
    }
}