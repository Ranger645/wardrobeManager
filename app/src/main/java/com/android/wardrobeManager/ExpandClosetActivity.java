package com.android.wardrobeManager;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.wardrobeManager.clothing_classes.Shorts;

import java.util.ArrayList;

public class ExpandClosetActivity extends AppCompatActivity {

    private ArrayList<ClothingItem> closetClothes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_closet);
        closetClothes = getIntent().getParcelableArrayListExtra("closetClothes");
        closetClothes.add(new Shorts(getResources().getColor(R.color.orange)));

    }


    public void goToPreviousActivity(View view) {
        Intent intent = new Intent();
        intent.putExtra("closetClothes", closetClothes);
        finish();
    }

    public void goToNewClothingItemActivity(View view) {
        Intent intent = new Intent(ExpandClosetActivity.this, NewClothingItemActivity.class);
        intent.putExtra("closetClothes", closetClothes);
        startActivity(intent);
    }
}
