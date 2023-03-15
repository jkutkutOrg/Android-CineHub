package org.cinehub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.model.SeatReservation;

public class ReservationItem extends Fragment {
    private static final String ARG_RESERVATION = "reservation";

    private SeatReservation reservation;

    public ReservationItem() {
        // Required empty public constructor
    }

    public static ReservationItem newInstance(SeatReservation reservation) {
        ReservationItem fragment = new ReservationItem();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RESERVATION, reservation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reservation = getArguments().getParcelable(ARG_RESERVATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reservation_item, container, false);
        ((TextView) v.findViewById(R.id.tvResItemPos))
                .setText(getString(R.string.label_seat_placement,
                        reservation.getRow(), reservation.getCol()));
        CinehubAPI api = new CinehubAPI();
        api.getProjection(reservation.getProjection(), projection -> {
            api.getMovie(projection.getMovie(), movie -> ((TextView) v
                    .findViewById(R.id.tvResItemFilm))
                    .setText(movie.getName()), System.err::println);
            ((TextView) v.findViewById(R.id.tvResItemRoom)).setText(String
                    .valueOf(projection.getRoom()));
            String time = projection.getTimedate().substring(0, 16).replace('T', ' ');
            ((TextView) v.findViewById(R.id.tvResItemTime)).setText(time);
        }, System.err::println);
        return v;
    }
}