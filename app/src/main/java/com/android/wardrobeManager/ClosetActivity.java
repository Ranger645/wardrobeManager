package com.android.wardrobeManager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ClosetActivity extends AppCompatActivity {


    private boolean shirtScrollVisible = false;
    private boolean shoeScrollVisible = false;
    private boolean shortScrollVisible = false;

    private String closetClothingTypeShow = "";
    private String closetClothingTypeHide = "";

    private View shirtScroll = null;
    private View shoeScroll = null;
    private View shortScroll = null;

    private View shirtToggle = null;
    private View shoeToggle = null;
    private View shortToggle = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);

        shirtScroll = findViewById(R.id.shirtScrollView);
        shoeScroll = findViewById(R.id.shoesScrollView);
        shortScroll = findViewById(R.id.shortsScrollView);

        shirtToggle = findViewById(R.id.shirtsToggleView);
        shoeToggle = findViewById(R.id.shoesToggleView);
        shortToggle = findViewById(R.id.shortsToggleView);

        shirtScroll.setVisibility(View.GONE);
        shoeScroll.setVisibility(View.GONE);
        shortScroll.setVisibility(View.GONE);

        closetClothingTypeShow = getResources().getString(R.string.closet_clothing_type_show);
        closetClothingTypeHide = getResources().getString(R.string.closet_clothing_type_hide);

    }

    public void backToMainScreen(View view) {
        startActivity(new Intent(ClosetActivity.this, StartActivity.class));
    }

    public void toggleShirtVisibility(View view) {

        if (shirtScrollVisible) {
            shirtScroll.setVisibility(View.GONE);
            ((TextView)shirtToggle).setText(closetClothingTypeHide);
        }
        else {
            shirtScroll.setVisibility(View.VISIBLE);
            ((TextView)shirtToggle).setText(closetClothingTypeShow);
        }

        shirtScrollVisible = !shirtScrollVisible;

    }

    public void goToExpandCloset(View view) {
        startActivity(new Intent(ClosetActivity.this, ExpandClosetActivity.class));
    }

    public void toggleShoesVisibility(View view) {

        if (shoeScrollVisible) {
            shoeScroll.setVisibility(View.GONE);
            ((TextView)shoeToggle).setText(closetClothingTypeHide);
        }
        else {
            shoeScroll.setVisibility(View.VISIBLE);
            ((TextView)shoeToggle).setText(closetClothingTypeShow);
        }
        shoeScrollVisible = !shoeScrollVisible;

    }

    public void toggleShortsVisibility(View view) {

        if (shortScrollVisible) {
            shortScroll.setVisibility(View.GONE);
            ((TextView)shortToggle).setText(closetClothingTypeHide);
        }
        else {
            shortScroll.setVisibility(View.VISIBLE);
            ((TextView)shortToggle).setText(closetClothingTypeShow);
        }

        shortScrollVisible = !shortScrollVisible;

    }

}
