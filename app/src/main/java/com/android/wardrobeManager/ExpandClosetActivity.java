package com.android.wardrobeManager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.wardrobeManager.clothing_classes.Shorts;

import java.util.ArrayList;

public class ExpandClosetActivity extends AppCompatActivity {

    private ArrayList<ClothingItem> closetClothes = null;
    private String previousActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_closet);
        closetClothes = getIntent().getParcelableArrayListExtra("closetClothes");
        previousActivity = getIntent().getStringExtra("previousActivity");

        closetClothes.add(new Shorts(getResources().getColor(R.color.orange)));

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
        startActivity(intent);
    }

    public void goToNewClothingItemActivity(View view) {
        Intent intent = new Intent(ExpandClosetActivity.this, NewClothingItemActivity.class);
        intent.putExtra("closetClothes", closetClothes);
        startActivity(intent);
    }
}
