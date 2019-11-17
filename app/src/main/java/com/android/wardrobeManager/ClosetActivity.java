package com.android.wardrobeManager;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.wardrobeManager.clothing_classes.Shirt;

import java.util.ArrayList;

public class ClosetActivity extends AppCompatActivity {


    private boolean shirtScrollVisible = false;
    private boolean shoeScrollVisible = false;
    private boolean shortScrollVisible = false;

    private String closetClothingTypeShow = "";
    private String closetClothingTypeHide = "";

    private View shirtScroll = null;
    private View shoeScroll = null;
    private View shortScroll = null;

    private TextView shirtToggle = null;
    private TextView shoeToggle = null;
    private TextView shortToggle = null;

    private ArrayList<ClothingItem> closetClothes = new ArrayList<ClothingItem>();

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

        findViewById(R.id.shirtScrollView).setVisibility(View.GONE);
        findViewById(R.id.shoesScrollView).setVisibility(View.GONE);
        findViewById(R.id.shortsScrollView).setVisibility(View.GONE);

        closetClothingTypeShow = getResources().getString(R.string.closet_clothing_type_show);
        closetClothingTypeHide = getResources().getString(R.string.closet_clothing_type_hide);

        if (getIntent().getParcelableArrayListExtra("closetClothes") != null) {
            closetClothes = getIntent().getParcelableArrayListExtra("closetClothes");
        }


    }

    public void backToMainScreen(View view) {
        Intent intent = new Intent(ClosetActivity.this, StartActivity.class);
        intent.putExtra("closetClothes", closetClothes);
        startActivity(intent);
    }

    public void toggleShirtVisibility(View view) {

        shirtScrollVisible = !shirtScrollVisible;
        if (!shirtScrollVisible) {
            shirtScroll.setVisibility(View.GONE);
            shirtToggle.setText(closetClothingTypeShow);
        } else {
            shirtScroll.setVisibility(View.VISIBLE);
            shirtToggle.setText(closetClothingTypeHide);
        }
    }

    public void goToExpandCloset(View view) {

        Intent intent = new Intent(ClosetActivity.this, ExpandClosetActivity.class);
        intent.putExtra("closetClothes", closetClothes);
        intent.putExtra("previousActivity", "ClosetActivity");
        startActivity(intent);
    }

    public void toggleShoesVisibility(View view) {

        shoeScrollVisible = !shoeScrollVisible;
        if (!shoeScrollVisible) {
            shoeScroll.setVisibility(View.GONE);
            shoeToggle.setText(closetClothingTypeShow);
        }
        else {
            shoeScroll.setVisibility(View.VISIBLE);
            shoeToggle.setText(closetClothingTypeHide);
        }


    }

    public void toggleShortsVisibility(View view) {

        shortScrollVisible = !shortScrollVisible;
        if (!shortScrollVisible) {
            shortScroll.setVisibility(View.GONE);
            shortToggle.setText(closetClothingTypeShow);
        }
        else {
            shortScroll.setVisibility(View.VISIBLE);
            shortToggle.setText(closetClothingTypeHide);
        }


    }

}
