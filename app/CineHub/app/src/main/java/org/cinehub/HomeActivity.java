package org.cinehub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.CinehubDB;
import org.cinehub.api.model.SeatReservation;
import org.cinehub.api.model.User;

public class HomeActivity extends NavActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView tvHomeTitle = findViewById(R.id.tvHomeTitle);
        TextView tvHomeSub = findViewById(R.id.tvHomeSub);
        FloatingActionButton fabAddTicket = findViewById(R.id.fabAddTicket);

        User user = getIntent().getParcelableExtra(LoginActivity.EXTRA_USER);
        CinehubDB api = CinehubAPI.getDBInstance();

        tvHomeTitle.setText(getString(R.string.title_home, user.getName()));
        api.getSeatReservationUser(user, reservations -> {
            if (reservations.size() == 0) {
                tvHomeSub.setText(R.string.msg_empty_booking);
                return ;
            }
            tvHomeSub.setText(getString(R.string.label_home_sub, reservations.size()));
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            for (SeatReservation reservation: reservations) {
                ReservationItem frag = ReservationItem.newInstance(reservation);
                transaction.add(R.id.cntHomeTickets, frag);
            }
            transaction.commit();
        }, System.err::println);
        fabAddTicket.setOnClickListener(v -> advanceActivity(() ->
                new Intent(this, BillBoardActivity.class)));
    }
}