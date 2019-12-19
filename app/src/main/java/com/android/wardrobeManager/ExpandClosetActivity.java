package com.android.wardrobeManager;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ExpandClosetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_closet);

    }


    public void goToPreviousActivity(View view) {
        finish();
    }

    public void goToNewClothingItemActivity(View view) {
        startActivity(new Intent(ExpandClosetActivity.this, NewClothingItemActivity.class));
    }
}
