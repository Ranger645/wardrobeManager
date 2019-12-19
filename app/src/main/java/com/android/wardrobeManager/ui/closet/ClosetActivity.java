package com.android.wardrobeManager.ui.closet;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.StartActivity;
import com.android.wardrobeManager.backend.ClothingItemViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.database.ClothingItemDatabase;

import java.util.List;

public class ClosetActivity extends AppCompatActivity {

    private ClothingItemViewModel clothingItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2 ));
        recyclerView.setHasFixedSize(true);

        final ClosetAdapter adapter = new ClosetAdapter();
        recyclerView.setAdapter(adapter);

        clothingItemViewModel = ViewModelProviders.of(this).get(ClothingItemViewModel.class);
        clothingItemViewModel.getClothingItems().observe(this, new Observer<List<ClothingItem>>() {
            @Override
            public void onChanged(List<ClothingItem> clothingItems) {
                adapter.setItems(clothingItems);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ClosetActivity.this, StartActivity.class));
    }

}
