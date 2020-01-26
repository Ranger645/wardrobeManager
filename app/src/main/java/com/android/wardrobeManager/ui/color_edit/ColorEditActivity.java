package com.android.wardrobeManager.ui.color_edit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;
import com.android.wardrobeManager.backend.ColorEditViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.add_item.AddItemActivity;
import com.android.wardrobeManager.ui.closet.ClosetActivity;
import com.android.wardrobeManager.ui.images.ClothingItemImageManager;
import com.android.wardrobeManager.ui.util.Utility;

public class ColorEditActivity extends AppCompatActivity {

    private boolean previewDisplayed = true;

    private static final String COLOR_EDIT_PREVIEW_FRAG_TAG = "COLOR_EDIT_PREVIEW";
    private static final String COLOR_EDIT_MANUAL_FRAG_TAG = "COLOR_EDIT_MANUAL";
    private ColorEditPreviewFragment colorEditPreviewFragment;
    private ColorEditManualFragment colorEditManualFragment;

    private ColorEditViewModel viewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_edit);

        viewModel = ViewModelProviders.of(this).get(ColorEditViewModel.class);
        Bundle bundle = getIntent().getExtras();

        // This parcelable will be passed when there is a particular item that needs to be edited.
        final ClothingItem clothingItem = bundle.getParcelable("clothingItem");
        if (clothingItem != null)
            viewModel.setClothingItem(clothingItem);
        if (viewModel.getClothingItem() == null) {
            Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT);
            viewModel.setClothingItem(new ClothingItem());
        }

        colorEditPreviewFragment = ColorEditPreviewFragment.getInstance(clothingItem);
        colorEditManualFragment = ColorEditManualFragment.getInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.color_edit_fragment_holder, colorEditPreviewFragment, COLOR_EDIT_PREVIEW_FRAG_TAG);
        ft.commit();

        View colorEditFunctionButton = findViewById(R.id.color_edit_function_button);
        colorEditFunctionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (previewDisplayed) {
                    switchColorEditFragments(COLOR_EDIT_MANUAL_FRAG_TAG);
                } else {
                    switchColorEditFragments(COLOR_EDIT_PREVIEW_FRAG_TAG);
                }
            }
        });

        viewModel.getClothingItem().observe(this, new Observer<ClothingItem>() {
            @Override
            public void onChanged(ClothingItem item) {
                if (colorEditPreviewFragment.getPreviewView() != null)
                    colorEditPreviewFragment.getPreviewView().setImageBitmap(ClothingItemImageManager.dynamicClothingItemLoad(clothingItem));

                updateColorDisplay(Utility.parseClothingItemColors(item.getColors()));
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (previewDisplayed) {
            backToAddItemActivity();
        } else {
            switchColorEditFragments(COLOR_EDIT_PREVIEW_FRAG_TAG);
        }
    }

    protected void backToAddItemActivity() {
        Intent intent = new Intent(ColorEditActivity.this, AddItemActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("clothingItem", viewModel.getClothingItem().getValue());
        intent.putExtras(bundle);
        Pair<View, String> p1 = new Pair<>(findViewById(R.id.color_edit_fragment_holder), "add_item_viewpager_transition");
        Pair<View, String> p2 = new Pair<>(findViewById(R.id.color_edit_button_display), "color_edit_button_transition");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ColorEditActivity.this, p1, p2);
        startActivity(intent, options.toBundle());
    }

    protected void updateColorDisplay(int[] colors) {
        LinearLayout displayLayout = findViewById(R.id.color_edit_button_display);
        displayLayout.removeAllViews();
        displayLayout.setBackgroundColor(Color.BLUE);
        for (int i = 0; i < colors.length; i++) {
            final ColorEditSingleColorButton colorButton = new ColorEditSingleColorButton(this, colors[i]);
            colorButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.removeClothingItemColor(((ColorEditSingleColorButton) v).getColor());
                }
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            colorButton.setLayoutParams(params);
            colorButton.setBackgroundColor(colors[i]);
            displayLayout.addView(colorButton);
        }
    }

    protected void switchColorEditFragments(String tagToSwitchTo) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        TextView buttonView = findViewById(R.id.color_edit_function_button);
        if (tagToSwitchTo.equals(COLOR_EDIT_PREVIEW_FRAG_TAG)) {
            ft.setCustomAnimations(R.anim.slide_from_top, R.anim.fade_out);
            colorEditPreviewFragment = ColorEditPreviewFragment.getInstance(viewModel.getClothingItem().getValue());
            ft.replace(R.id.color_edit_fragment_holder, colorEditPreviewFragment, tagToSwitchTo);
            buttonView.setText("Custom Color");
        } else {
            ft.setCustomAnimations(R.anim.fade_in, R.anim.slide_towards_top);
            colorEditManualFragment = ColorEditManualFragment.getInstance();
            ft.replace(R.id.color_edit_fragment_holder, colorEditManualFragment, tagToSwitchTo);
            buttonView.setText("Save Colors");
        }
        ft.commit();
        previewDisplayed = !previewDisplayed;
    }
}
