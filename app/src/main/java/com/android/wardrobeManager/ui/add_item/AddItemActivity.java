package com.android.wardrobeManager.ui.add_item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.android.wardrobeManager.R;
import com.android.wardrobeManager.backend.AddItemViewModel;
import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.closet.ClosetActivity;
import com.android.wardrobeManager.ui.images.ClothingItemImageManager;
import com.android.wardrobeManager.ui.util.WardrobeAlerts;
import com.android.wardrobeManager.ui.util.WardrobeAlerts.*;

public class AddItemActivity extends AppCompatActivity {

    private ViewPager addItemViewPager = null;
    private AddItemViewPagerAdapter addItemViewPagerAdapter = null;

    private AddItemViewModel addItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        addItemViewModel = ViewModelProviders.of(this).get(AddItemViewModel.class);
        Bundle bundle = getIntent().getExtras();

        // This parcelable will be passed when there is a particular item that needs to be edited.
        ClothingItem clothingItem = bundle.getParcelable("clothingItem");
        if (clothingItem != null)
            addItemViewModel.setClothingItem(clothingItem);
        if (addItemViewModel.getClothingItem() == null)
            addItemViewModel.setClothingItem(new ClothingItem());

        addItemViewPager = findViewById(R.id.add_item_view_pager);
        addItemViewPagerAdapter = new AddItemViewPagerAdapter(
                getSupportFragmentManager(),
                AddItemViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                addItemViewModel.getClothingItem());
        addItemViewPager.setAdapter(addItemViewPagerAdapter);

        refreshUIForClothingItem(addItemViewModel.getClothingItem());
    }

    private void refreshUIForClothingItem(ClothingItem clothingItem) {

    }

    @Override
    public void onBackPressed() {

        ///////////////////////////////////////////////////////////////
        // Temporary solution until x and check buttons are finished //
        ///////////////////////////////////////////////////////////////

        final String saveTag = "Save";
        final String quitTag = "Quit";

        RadioButtonAlertCallback callback = new RadioButtonAlertCallback() {
            public void onClick(Context context, String selectedItem, int itemIndex) {

                // TODO: Make this actually save to the closet.
                if (selectedItem.equals(saveTag)) {

                } else if (selectedItem.equals(quitTag)) {

                } else {

                }

                Intent intent = new Intent(AddItemActivity.this, ClosetActivity.class);
                startActivity(intent);
            }
        };
        WardrobeAlerts.showRadioButtonDialog(this, "Quit", new String[] {saveTag, quitTag}, callback);

        ///////////////////////////////////////////////////////////////
    }
}
