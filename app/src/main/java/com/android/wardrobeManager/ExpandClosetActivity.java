package com.android.wardrobeManager;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpandClosetActivity extends AppCompatActivity {

    private ArrayList<ClothingItem> closetClothes = null;
    private ClothingItem newClothingItem = null;
    private String previousActivity = null;
    private ImageView newClothingItemImage = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_closet);
        closetClothes = getIntent().getParcelableArrayListExtra("closetClothes");
        previousActivity = getIntent().getStringExtra("previousActivity");
        newClothingItem = getIntent().getParcelableExtra("newClothingItem");
        newClothingItemImage = findViewById(R.id.newClothingItemImage);

        if (newClothingItem != null) {

            String clothingType = newClothingItem.clothingTypeToString();

            switch (clothingType) {
                case "Shirt":
                    newClothingItemImage.setImageResource(R.drawable.gray_shirt);
                    break;
                case "Shoes":
                    newClothingItemImage.setImageResource(R.drawable.gray_shoes);
                    break;
                case "Shorts":
                    newClothingItemImage.setImageResource(R.drawable.gray_shorts);
                    break;
            }
            updateColor();
        }

    }

    public void updateColor() {

        newClothingItemImage.setColorFilter(newClothingItem.getColor(), PorterDuff.Mode.OVERLAY);

    }

    public void addItemToCloset(View view) {
        if (newClothingItem != null) {
            closetClothes.add(newClothingItem);

            ((TextView)view).setText("Add duplicate\nto closet");
        }
    }


    public void goToPreviousActivity(View view) {
        Class previousActivityClass;

        switch (previousActivity) {
            case "ClosetActivity":
                previousActivityClass = ClosetActivity.class;
                break;
            case "StartActivity":
                previousActivityClass = StartActivity.class;
                break;
            default:
                previousActivityClass = StartActivity.class;
                break;
        }

        Intent intent = new Intent(ExpandClosetActivity.this, previousActivityClass);
        intent.putExtra("closetClothes", closetClothes);
        intent.putExtra("previousActivity", previousActivity);
        intent.putExtra("newClothingItem", newClothingItem);
        startActivity(intent);
    }

    public void goToNewClothingItemActivity(View view) {
        Intent intent = new Intent(ExpandClosetActivity.this, NewClothingItemActivity.class);
        intent.putExtra("closetClothes", closetClothes);
        intent.putExtra("previousActivity", previousActivity);
        intent.putExtra("newClothingItem", newClothingItem);
        startActivity(intent);
    }
}
