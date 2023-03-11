package org.cinehub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class BookingSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_summary);

        TextView tvMovieName = findViewById(R.id.tvMovieName);
        TextView tvDate = findViewById(R.id.tvDate);
        TextView tvTime = findViewById(R.id.tvTime);
        TextView tvRoom = findViewById(R.id.tvRoom);
        TextView tvSeat = findViewById(R.id.tvSeat);
        TextView tvPrice = findViewById(R.id.tvPrice);
        // TODO assign data from previous activities

        findViewById(R.id.btnBookingConfirmation).setOnClickListener(v -> onBookingConfirmation());
    }

    private void onBookingConfirmation() {
        // TODO send data to db
    }
}