package com.android.wardrobeManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.wardrobeManager.clothing_classes.Shirt;
import com.android.wardrobeManager.clothing_classes.Shoes;
import com.android.wardrobeManager.clothing_classes.Shorts;

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

    private LinearLayout shirtLayout;
    private LinearLayout shoesLayout;
    private LinearLayout shortsLayout;

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

        shirtLayout = findViewById(R.id.laundryShirtsList);
        shoesLayout = findViewById(R.id.laundryShoesList);
        shortsLayout = findViewById(R.id.laundryShortsList);

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

        addItemsToSections();

    }

    private void addItemsToSections() {
        for (int i = 0; i < laundryClothes.size(); i++) {

            ImageView item = new ImageView(this);
            int imageSize = (int) convertDpToPx(this, 150f);

            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(imageSize, imageSize);
            int marginInPixels = (int) convertDpToPx(this, 5f);
            params.topMargin = marginInPixels;
            params.bottomMargin = marginInPixels;
            params.leftMargin = marginInPixels;
            params.rightMargin = marginInPixels;
            item.setLayoutParams(params);

            if (laundryClothes.get(i) instanceof Shirt) {
                item.setImageResource(R.drawable.gray_shirt);
                item.setColorFilter(laundryClothes.get(i).getColor(), PorterDuff.Mode.OVERLAY);
                shirtLayout.addView(item);
            } else if (laundryClothes.get(i) instanceof Shorts) {
                item.setImageResource(R.drawable.gray_shorts);
                item.setColorFilter(laundryClothes.get(i).getColor(), PorterDuff.Mode.OVERLAY);
                shortsLayout.addView(item);
            } else if (laundryClothes.get(i) instanceof Shoes) {
                item.setImageResource(R.drawable.gray_shoes);
                item.setColorFilter(laundryClothes.get(i).getColor(), PorterDuff.Mode.OVERLAY);
                shoesLayout.addView(item);
            }

        }
    }

    public float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
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
