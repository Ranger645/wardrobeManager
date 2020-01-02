package com.android.wardrobeManager;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.Window;

import com.android.wardrobeManager.backend.ClothingItemViewModel;
import com.android.wardrobeManager.ui.closet.ClosetActivity;
import com.android.wardrobeManager.ui.images.ClothingItemImageManager;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {

    /*
     * Variable for enabling quickstart
     *
     * Quickstart should be enabled most of the time. It should be disabled in any of the following
     * cases:
     *  App is restarting after a crash
     *  App is starting for the first time after an update
     */
    public boolean quickStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start);

        // Scheduling the closet screen to be opened after the splash screen displays for some time
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, ClosetActivity.class));
            }
        }, getApplicationContext().getResources().getInteger(R.integer.splash_screen_min_time_millis));

        if (!quickStart)
            ClothingItemImageManager.deleteAllClothingItemImages(this.getApplication());
        ViewModelProviders.of(this).get(ClothingItemViewModel.class);

    }

}
