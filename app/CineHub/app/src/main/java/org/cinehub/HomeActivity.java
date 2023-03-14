package org.cinehub;

import android.os.Bundle;
import android.widget.TextView;

import org.cinehub.api.CinehubAPI;
import org.cinehub.api.model.User;

public class HomeActivity extends NavActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView tvHomeTitle = findViewById(R.id.tvHomeTitle);
        TextView tvHomeSub = findViewById(R.id.tvHomeSub);

        User user = getIntent().getParcelableExtra(LoginActivity.EXTRA_USER);
        CinehubAPI api = new CinehubAPI();

        tvHomeTitle.setText(getString(R.string.title_home, user.getName()));
        // TODO implement reservation view
    }
}