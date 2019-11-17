package com.android.wardrobeManager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.wardrobeManager.clothing_classes.Shirt;
import com.android.wardrobeManager.clothing_classes.Shoes;
import com.android.wardrobeManager.clothing_classes.Shorts;

import java.util.ArrayList;

public class NewClothingItemActivity extends AppCompatActivity {

    private Spinner itemTypeSpinner = null;
    private int color;
    private ColorStateList colorStateList = null;
    private String clothingType = "";
    private ImageView imagePreview = null;
    private View colorPreview = null;
    private ArrayList<ClothingItem> closetClothes = null;
    private ClothingItem newClothingItem = null;
    private String previousActivity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_clothing_item);

        closetClothes = getIntent().getParcelableArrayListExtra("closetClothes");
        previousActivity = getIntent().getStringExtra("previousActivity");
        if (getIntent().getParcelableExtra("newClothingItem") != null) {
            newClothingItem = getIntent().getParcelableExtra("newClothingItem");
            color = newClothingItem.getColor();
        }
        else {
            color = getResources().getColor(R.color.red);
            clothingType = "Shirt";
        }

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

        if (getIntent().getParcelableExtra("newClothingItem") != null) {
            clothingType = ((ClothingItem)getIntent().getParcelableExtra("newClothingItem")).clothingTypeToString();
            changeSpinnerSelection();
        }
        else {
            clothingType = "Shirt";
        }

        updateImagePreviewClothingType();
        updateColor();

    }

    public void colorSelected(View view) {
        colorStateList = findViewById(view.getId()).getBackgroundTintList();
        color = colorStateList.getDefaultColor();
        updateColor();
    }

    public void itemSelected(String clothingType) {
        this.clothingType = clothingType;

        updateImagePreviewClothingType();
        updateColor();
    }

    private void updateImagePreviewClothingType() {

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
    }

    public void changeSpinnerSelection() {
        switch (clothingType) {
            case "Shirt":
                itemTypeSpinner.setSelection(0);
                break;
            case "Shoes":
                itemTypeSpinner.setSelection(1);
                break;
            case "Shorts":
                itemTypeSpinner.setSelection(2);
                break;
        }
    }

    public void updateColor() {

        colorPreview.setBackgroundTintList(colorStateList);
        imagePreview.setColorFilter(color, PorterDuff.Mode.OVERLAY);

    }

    public void goToPreviousActivity(View view) {
        Intent intent = new Intent(NewClothingItemActivity.this, ExpandClosetActivity.class);
        intent.putExtra("closetClothes", closetClothes);
        intent.putExtra("newClothingItem", newClothingItem);
        intent.putExtra("previousActivity", previousActivity);
        startActivity(intent);
    }

    public void setClothingItem(View view) {

        switch (clothingType) {
            case "Shirt":
                newClothingItem = new Shirt(color);
                break;
            case "Shoes":
                newClothingItem = new Shoes(color);
                break;
            case "Shorts":
                newClothingItem = new Shorts(color);
                break;
        }

        goToPreviousActivity(view);
    }

}

