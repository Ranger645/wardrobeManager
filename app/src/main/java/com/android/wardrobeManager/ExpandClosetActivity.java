package com.android.wardrobeManager;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import java.util.ArrayList;

public class ExpandClosetActivity extends AppCompatActivity {

    private ArrayList<ClothingItem> closetClothes = null;
    private ArrayList<ClothingItem> laundryClothes = null;
    private ClothingItem newClothingItem = null;
    private String previousActivity = null;
    private ImageView newClothingItemImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_closet);

        if (getIntent().getParcelableArrayListExtra("closetClothes") != null) {
            closetClothes = getIntent().getParcelableArrayListExtra("closetClothes");
        } else {
            closetClothes = new ArrayList<ClothingItem>();
        }

        if (getIntent().getParcelableArrayListExtra("laundryClothes") != null) {
            laundryClothes = getIntent().getParcelableArrayListExtra("laundryClothes");
        } else {
            laundryClothes = new ArrayList<ClothingItem>();
        }

        previousActivity = getIntent().getStringExtra("previousActivity");
        newClothingItem = getIntent().getParcelableExtra("newClothingItem");
        newClothingItemImage = findViewById(R.id.newClothingItemImage);

        if (newClothingItem != null) {

            String clothingType = newClothingItem.clothingTypeToString();
            String imageName = "gray_" + clothingType.toLowerCase();
            newClothingItemImage.setImageResource(getResources().getIdentifier(imageName, "drawable", this.getPackageName()));

            // This takes the color from the newClothingItem object and uses it to change the gray
            // pixels in the image of the shirt to be that color when using the OVERLAY format
            newClothingItemImage.setColorFilter(newClothingItem.getColor(), PorterDuff.Mode.OVERLAY);

        }
    }

    public void addItemToCloset(View view) {
        if (newClothingItem != null) {
            closetClothes.add(newClothingItem);
            newClothingItem = null;
            newClothingItemImage.setImageResource(R.drawable.no_clothing_item_selected);
            newClothingItemImage.setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.OVERLAY);
        }
    }

    public void goToPreviousActivity(View view) {

        Class previousActivityClass;

        switch (previousActivity) {
            case "StartActivity":
                previousActivityClass = StartActivity.class;
                break;
            case "ClosetActivity":
                previousActivityClass = ClosetActivity.class;
                break;
            default:
                previousActivityClass = StartActivity.class;
                break;
        }

        Intent intent = new Intent(ExpandClosetActivity.this, previousActivityClass);
        intent.putExtra("closetClothes", closetClothes);
        intent.putExtra("laundryClothes", laundryClothes);
        intent.putExtra("newClothingItem", newClothingItem);
        startActivity(intent);
    }

    public void goToNewClothingItemActivity(View view) {
        Intent intent = new Intent(ExpandClosetActivity.this, NewClothingItemActivity.class);
        intent.putExtra("closetClothes", closetClothes);
        intent.putExtra("laundryClothes", laundryClothes);
        intent.putExtra("previousActivity", previousActivity);
        intent.putExtra("newClothingItem", newClothingItem);
        startActivity(intent);
    }
}
