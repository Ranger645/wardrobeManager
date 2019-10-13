package com.android.wardrobeManager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ClosetActivity extends AppCompatActivity {


    boolean shirtScrollVisible = false;
    boolean shoeScrollVisible = false;
    boolean shortScrollVisible = false;

    String buttonToggleOn;
    String buttonToggleOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);
        findViewById(R.id.shirtScrollView).setVisibility(View.GONE);
        findViewById(R.id.shoesScrollView).setVisibility(View.GONE);
        findViewById(R.id.shortsScrollView).setVisibility(View.GONE);

        buttonToggleOn = getResources().getString(R.string.closet_toggle_on);
        buttonToggleOff = getResources().getString(R.string.closet_toggle_off);

    }

    public void backToMainScreen(View view) {
        startActivity(new Intent(ClosetActivity.this, StartActivity.class));
    }

    public void toggleShirtVisibility(View view) {

        if (shirtScrollVisible) {
            shirtScrollVisible = false;
            findViewById(R.id.shirtScrollView).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.shirtsToggleView)).setText(buttonToggleOff);
        }
        else {
            shirtScrollVisible = true;
            findViewById(R.id.shirtScrollView).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.shirtsToggleView)).setText(buttonToggleOn);
        }


    }

    public void goToExpandCloset(View view) {
        startActivity(new Intent(ClosetActivity.this, ExpandClosetActivity.class));
    }

    public void toggleShoesVisibility(View view) {

        if (shoeScrollVisible) {
            shoeScrollVisible = false;
            findViewById(R.id.shoesScrollView).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.shoesToggleView)).setText(buttonToggleOff);
        }
        else {
            shoeScrollVisible = true;
            findViewById(R.id.shoesScrollView).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.shoesToggleView)).setText(buttonToggleOn);
        }


    }

    public void toggleShortsVisibility(View view) {

        if (shortScrollVisible) {
            shortScrollVisible = false;
            findViewById(R.id.shortsScrollView).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.shortsToggleView)).setText(buttonToggleOff);
        }
        else {
            shortScrollVisible = true;
            findViewById(R.id.shortsScrollView).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.shortsToggleView)).setText(buttonToggleOn);
        }


    }

}
