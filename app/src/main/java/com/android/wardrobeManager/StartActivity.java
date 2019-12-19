package com.android.wardrobeManager;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.wardrobeManager.ui.closet.ClosetActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


    }

    public void goToCloset(View view) {
        startActivity(new Intent(StartActivity.this, ClosetActivity.class));
    }

    public void goToExpandCloset(View view) {
        startActivity(new Intent(StartActivity.this, ExpandClosetActivity.class));
    }

}
