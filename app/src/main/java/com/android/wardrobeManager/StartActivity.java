package com.android.wardrobeManager;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.android.wardrobeManager.ui.closet.ClosetActivity;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends Activity {

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

    }

}
