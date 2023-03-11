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
    This method should be overridden, so it can insert the current's activity intent data
    into the intent to be launched on 'advanceActivity'
     */
    @NonNull
    protected abstract Intent collectData(@NonNull Intent intent);

    /*
    intentSupplier should be a lambda defined on the caller, which returns the 'default' intent
    with the current activity data about to be launched
     */
    protected void advanceActivity(@NonNull Supplier<Intent> intentSupplier) {
        startActivity(collectData(intentSupplier.get()));
    }
}
