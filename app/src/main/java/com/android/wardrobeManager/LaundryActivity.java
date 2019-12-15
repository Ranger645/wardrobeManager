package com.android.wardrobeManager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class LaundryActivity extends AppCompatActivity {

    private boolean shirtScrollVisible = false;
    private boolean shoeScrollVisible = false;
    private boolean shortScrollVisible = false;

    private String laundryClothingTypeShow = "";
    private String laundryClothingTypeHide = "";

    private View shirtScroll = null;
    private View shoeScroll = null;
    private View shortScroll = null;

    private TextView shirtToggle = null;
    private TextView shoeToggle = null;
    private TextView shortToggle = null;

    private ArrayList<ClothingItem> laundryClothes;
    private ArrayList<ClothingItem> closetClothes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry);

        shirtScroll = findViewById(R.id.shirtScrollView);
        shoeScroll = findViewById(R.id.shoesScrollView);
        shortScroll = findViewById(R.id.shortsScrollView);

        shirtToggle = findViewById(R.id.shirtsToggleView);
        shoeToggle = findViewById(R.id.shoesToggleView);
        shortToggle = findViewById(R.id.shortsToggleView);

        findViewById(R.id.shirtScrollView).setVisibility(View.GONE);
        findViewById(R.id.shoesScrollView).setVisibility(View.GONE);
        findViewById(R.id.shortsScrollView).setVisibility(View.GONE);

        laundryClothingTypeShow = getResources().getString(R.string.closet_clothing_type_show);
        laundryClothingTypeHide = getResources().getString(R.string.closet_clothing_type_hide);

        if (getIntent().getParcelableArrayListExtra("laundryClothes") != null) {
            laundryClothes = getIntent().getParcelableArrayListExtra("laundryClothes");
        } else {
            laundryClothes = new ArrayList<ClothingItem>();
        }

        if (getIntent().getParcelableArrayListExtra("closetClothes") != null) {
            closetClothes = getIntent().getParcelableArrayListExtra("closetClothes");
        } else {
            closetClothes = new ArrayList<ClothingItem>();
        }

    }

    public void backToMainScreen(View view) {
        Intent intent = new Intent(LaundryActivity.this, StartActivity.class);
        intent.putExtra("laundryClothes", laundryClothes);
        intent.putExtra("closetClothes", closetClothes);
        startActivity(intent);
    }

    public void toggleShirtVisibility(View view) {

        shirtScrollVisible = !shirtScrollVisible;
        if (!shirtScrollVisible) {
            shirtScroll.setVisibility(View.GONE);
            shirtToggle.setText(laundryClothingTypeShow);
        } else {
            shirtScroll.setVisibility(View.VISIBLE);
            shirtToggle.setText(laundryClothingTypeHide);
        }

    }

    public void toggleShoesVisibility(View view) {

        shoeScrollVisible = !shoeScrollVisible;
        if (!shoeScrollVisible) {
            shoeScroll.setVisibility(View.GONE);
            shoeToggle.setText(laundryClothingTypeShow);
        } else {
            shoeScroll.setVisibility(View.VISIBLE);
            shoeToggle.setText(laundryClothingTypeHide);
        }

    }

    public void toggleShortsVisibility(View view) {

        shortScrollVisible = !shortScrollVisible;
        if (!shortScrollVisible) {
            shortScroll.setVisibility(View.GONE);
            shortToggle.setText(laundryClothingTypeShow);
        } else {
            shortScroll.setVisibility(View.VISIBLE);
            shortToggle.setText(laundryClothingTypeHide);
        }

    }

}
