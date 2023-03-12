package org.cinehub;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.function.Supplier;

/*
Describes an Activity which allows moving forward while preserving its intent data
 */
public abstract class NavActivity extends AppCompatActivity {

    /*
    intentSupplier should be a lambda defined on the caller, which returns the 'default' intent
    with the current activity data about to be launched
     */
    protected void advanceActivity(@NonNull Supplier<Intent> intentSupplier) {
        startActivity(intentSupplier.get().putExtras(getIntent()));
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent.putExtras(getIntent()));
    }
}
