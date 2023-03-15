package org.cinehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.CinehubDB;
import org.cinehub.api.model.Reservation;
import org.cinehub.api.model.SeatReservation;

public class ReservationItem extends Fragment {
    private static final String ARG_RESERVATION = "reservation";

    private Integer reservationId;

    public ReservationItem() {
        // Required empty public constructor
    }

    public static ReservationItem newInstance(int reservation) {
        ReservationItem fragment = new ReservationItem();
        Bundle args = new Bundle();
        args.putInt(ARG_RESERVATION, reservation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reservationId = getArguments().getInt(ARG_RESERVATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reservation_item, container, false);
        CinehubDB api = CinehubAPI.getDBInstance();

        TextView tvSeats = v.findViewById(R.id.tvResItemPos);
        TextView tvMovie = v.findViewById(R.id.tvResItemFilm);
        TextView tvRoom = v.findViewById(R.id.tvResItemRoom);
        TextView tvTime = v.findViewById(R.id.tvResItemTime);

        System.out.println("Reservation id: " + reservationId);

        api.getSeatReservationReservation(
            reservationId,
            seats -> {
                StringBuilder sb = new StringBuilder();
                for (SeatReservation sr: seats) {
                    sb.append(String.format(
                        getString(R.string.label_seat_placement) + "; ",
                        sr.getRow(), sr.getCol()
                    ));
                }
                tvSeats.setText(sb.toString());

                api.getProjection(
                    seats.get(0).getProjection(),
                    projection -> {
                        api.getMovie(
                            projection.getMovie(),
                            movie -> {
                                tvMovie.setText(movie.getName());
                            },
                            System.err::println
                        );
                        tvRoom.setText(String.valueOf(projection.getRoom()));
                        String time = projection.getTimedate()
                            .substring(0, 16).replace('T', ' ');
                        tvTime.setText(time);
                    }, System.err::println
                );
            },
            System.err::println
        );
        v.setOnClickListener(v1 -> {
            Intent i = new Intent(getActivity(), EndActivity.class);
            i.putExtra("reservationId", reservationId);
            startActivity(i);
        });
        return v;
    }
}