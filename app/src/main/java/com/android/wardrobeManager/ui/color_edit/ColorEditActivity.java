package com.android.wardrobeManager.ui.color_edit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.add_item.AddItemActivity;
import com.android.wardrobeManager.ui.images.ClothingItemImageManager;
import com.android.wardrobeManager.ui.util.Utility;

public class ColorEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_edit);

        final AddItemViewModel addItemViewModel = ViewModelProviders.of(this).get(AddItemViewModel.class);
        Bundle bundle = getIntent().getExtras();

        // This parcelable will be passed when there is a particular item that needs to be edited.
        final ClothingItem clothingItem = bundle.getParcelable("clothingItem");
        if (clothingItem != null)
            addItemViewModel.setClothingItem(clothingItem);
        if (addItemViewModel.getClothingItem() == null) {
            Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT);
            addItemViewModel.setClothingItem(new ClothingItem());
        }

        final ColorEditPreview previewView = new ColorEditPreview(this, ClothingItemImageManager.dynamicClothingItemLoad(
                getApplication(), clothingItem), clothingItem.isCustomImage());
        previewView.setColorSelectListener(new ColorEditPreview.ColorSelectListener() {
            @Override
            public void onColorSelect(int color) {
                addClothingItemColor(0xFF000000 | color);
            }
        });
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT);
        previewView.setLayoutParams(params);
        FrameLayout previewDisplayLayout = findViewById(R.id.color_edit_preview_frame);
        previewDisplayLayout.addView(previewView);

        final ManualColorSelectorView manualSelector = new ManualColorSelectorView(this);
        params = new FrameLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT);
        manualSelector.setLayoutParams(params);
        FrameLayout manualSelectLayout = findViewById(R.id.manual_color_select_frame);
        manualSelectLayout.addView(manualSelector);

        addItemViewModel.getClothingItem().observe(this, new Observer<ClothingItem>() {
            @Override
            public void onChanged(ClothingItem item) {
                previewView.setImageBitmap(ClothingItemImageManager.dynamicClothingItemLoad(
                        getApplication(), clothingItem));

                updateColorDisplay(item);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ColorEditActivity.this, AddItemActivity.class);
        Bundle bundle = new Bundle();
        final AddItemViewModel addItemViewModel = ViewModelProviders.of(this).get(AddItemViewModel.class);
        bundle.putParcelable("clothingItem", addItemViewModel.getClothingItem().getValue());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void updateColorDisplay(ClothingItem item) {
        int[] colors = Utility.parseClothingItemColors(item.getColors());
        LinearLayout displayLayout = findViewById(R.id.color_edit_button_display);
        displayLayout.removeAllViews();
        displayLayout.setBackgroundColor(Color.BLUE);
        for (int i = 0; i < colors.length; i++) {
            final ColorEditSingleColorButton colorButton = new ColorEditSingleColorButton(this, colors[i]);
            colorButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeClothingItemColor(((ColorEditSingleColorButton) v).getColor());
                }
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            colorButton.setLayoutParams(params);
            colorButton.setBackgroundColor(colors[i]);
            displayLayout.addView(colorButton);
        }
    }

    private void removeClothingItemColor(int color) {
        AddItemViewModel addItemViewModel = ViewModelProviders.of(this).get(AddItemViewModel.class);
        ClothingItem item = addItemViewModel.getClothingItem().getValue();
        int[] colors = Utility.parseClothingItemColors(item.getColors());
        if (colors.length <= 1)
            return;
        StringBuilder newColors = new StringBuilder();
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] != color) {
                newColors.append(Integer.toHexString(colors[i]));
                if (i < colors.length - 1)
                    newColors.append(",");
            }
        }
        item.setColors(newColors.toString());
        addItemViewModel.setClothingItem(item);
    }

    private void addClothingItemColor(int color) {
        AddItemViewModel addItemViewModel = ViewModelProviders.of(this).get(AddItemViewModel.class);
        ClothingItem item = addItemViewModel.getClothingItem().getValue();
        StringBuilder builder = new StringBuilder(item.getColors());
        builder.append(",");
        builder.append(Integer.toHexString(color));
        item.setColors(builder.toString());
        addItemViewModel.setClothingItem(item);
    }
}
