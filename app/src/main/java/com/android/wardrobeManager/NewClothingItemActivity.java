package com.android.wardrobeManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewClothingItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Stuff");
        setContentView(R.layout.activity_new_clothing_item);

    }


    public void goToPreviousActivity(View view) {
        finish();
    }
}
