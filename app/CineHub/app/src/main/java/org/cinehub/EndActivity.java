package org.cinehub;

import android.content.Intent;
import android.os.Bundle;

public class EndActivity extends NavActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        findViewById(R.id.btnEndReturn).setOnClickListener(v -> advanceActivity(() ->
            new Intent(this, HomeActivity.class)));
    }
}