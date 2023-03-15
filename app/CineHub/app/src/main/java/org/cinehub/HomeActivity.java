package org.cinehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.CinehubAuth;
import org.cinehub.api.CinehubDB;
import org.cinehub.api.model.User;

public class HomeActivity extends NavActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView tvHomeTitle = findViewById(R.id.tvHomeTitle);
        FloatingActionButton fabAddTicket = findViewById(R.id.fabAddTicket);

        tvHomeTitle.setText(getString(R.string.wellcome));

        CinehubAuth auth = CinehubAPI.getAuthInstance();
        auth.whoami(this::updateUI, System.err::println);
        fabAddTicket.setOnClickListener(v -> advanceActivity(() ->
                new Intent(this, BillBoardActivity.class)));
    }

    private void updateUI(User user) {
        TextView tvHomeSub = findViewById(R.id.tvHomeSub);
        CinehubDB api = CinehubAPI.getDBInstance();
        api.getReservationsIdsUser(
            user,
            reservations -> {
                if (reservations.size() == 0) {
                    tvHomeSub.setText(R.string.msg_empty_booking);
                    return;
                }
                tvHomeSub.setText(getString(R.string.label_home_sub, reservations.size()));
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                for (int i = 0; i < reservations.size(); i++) {
                    transaction.add(
                        R.id.cntHomeTickets,
                        ReservationItem.newInstance(reservations.get(i))
                    );
                }
                transaction.commit();
            },
            System.err::println
        );
    }
}