package com.android.wardrobeManager;

import android.content.Intent;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClosetActivity extends AppCompatActivity {


    boolean shirtsScrollVisible = false;
    boolean shoesScrollVisible = false;
    boolean shortsScrollVisible = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);
        findViewById(R.id.shirtScrollView).setVisibility(View.GONE);
        findViewById(R.id.shoesScrollView).setVisibility(View.GONE);
        findViewById(R.id.shortsScrollView).setVisibility(View.GONE);


    }

    public void backToMainScreen(View view) {
        startActivity(new Intent(ClosetActivity.this, StartActivity.class));
    }

    public void toggleShirtVisibility(View view) {

        if (shirtsScrollVisible) {
            shirtsScrollVisible = false;
            findViewById(R.id.shirtScrollView).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.shirtsToggleView)).setText(">");
        }
        else {
            shirtsScrollVisible = true;
            findViewById(R.id.shirtScrollView).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.shirtsToggleView)).setText("v");
        }


    }

    public void goToExpandCloset(View view) {
        startActivity(new Intent(ClosetActivity.this, ExpandClosetActivity.class));
    }

    public void toggleShoesVisibility(View view) {

        if (shoesScrollVisible) {
            shoesScrollVisible = false;
            findViewById(R.id.shoesScrollView).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.shoesToggleView)).setText(">");
        }
        else {
            shoesScrollVisible = true;
            findViewById(R.id.shoesScrollView).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.shoesToggleView)).setText("v");
        }


    }

    public void toggleShortsVisibility(View view) {

        if (shortsScrollVisible) {
            shortsScrollVisible = false;
            findViewById(R.id.shortsScrollView).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.shortsToggleView)).setText(">");
        }
        else {
            shortsScrollVisible = true;
            findViewById(R.id.shortsScrollView).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.shortsToggleView)).setText("v");
        }


    }

}
