package com.android.wardrobeManager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
