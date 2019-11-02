package com.android.wardrobeManager;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class NewClothingItemActivity extends AppCompatActivity {

    ClothingItem newClothingItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_clothing_item);

        int defaultColor = getResources().getColor(R.color.red);

        newClothingItem = new ClothingItem(defaultColor, "shirt");
        ((ImageView)findViewById(R.id.newClothingItemImagePreview)).setColorFilter(defaultColor, PorterDuff.Mode.OVERLAY);

    }

    public void updateColor(View view) {

        ColorStateList updateColor = findViewById(view.getId()).getBackgroundTintList();
        findViewById(R.id.colorPreview).setBackgroundTintList(updateColor);

        int color = updateColor.getDefaultColor();
        ((ImageView)findViewById(R.id.newClothingItemImagePreview)).setColorFilter(color, PorterDuff.Mode.OVERLAY);



    }

    public void goToPreviousActivity(View view) {
        finish();
    }

}