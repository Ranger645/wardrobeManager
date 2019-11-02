package com.android.wardrobeManager;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

public class NewClothingItemActivity extends AppCompatActivity {

    Spinner itemTypeSpinner = null;
    int color;
    ColorStateList colorStateList = null;
    String clothingType = "";
    ImageView imagePreview = null;
    View colorPreview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_clothing_item);

        color = getResources().getColor(R.color.red);

        imagePreview = findViewById(R.id.newClothingItemImagePreview);
        imagePreview.setColorFilter(color, PorterDuff.Mode.OVERLAY);

        int[][] states = new int[][] {
                new int[] { android.R.attr.state_enabled}, // enabled
                new int[] {-android.R.attr.state_enabled}, // disabled
                new int[] {-android.R.attr.state_checked}, // unchecked
                new int[] { android.R.attr.state_pressed}  // pressed
        };

        int[] colors = new int[] {color, color, color, color};

        colorStateList = new ColorStateList(states, colors);

        colorPreview = findViewById(R.id.colorPreview);
        colorPreview.setBackgroundTintList(colorStateList);

        itemTypeSpinner = findViewById(R.id.clothingTypeSpinner);
        itemTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSpinnerText = itemTypeSpinner.getSelectedItem().toString();
                itemSelected(selectedSpinnerText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }

    public void colorSelected(View view) {
        colorStateList = findViewById(view.getId()).getBackgroundTintList();
        color = colorStateList.getDefaultColor();
        updateColor();
    }

    public void itemSelected(String clothingType) {
        this.clothingType = clothingType;

        switch (clothingType) {
            case "Shirt":
                imagePreview.setImageResource(R.drawable.gray_shirt);
                break;
            case "Shoes":
                imagePreview.setImageResource(R.drawable.gray_shoes);
                break;
            case "Shorts":
                imagePreview.setImageResource(R.drawable.gray_shorts);
                break;
        }
        updateColor();
    }

    public void updateColor() {

        colorPreview.setBackgroundTintList(colorStateList);
        imagePreview.setColorFilter(color, PorterDuff.Mode.OVERLAY);

    }

    public void goToPreviousActivity(View view) {
        finish();
    }


}

