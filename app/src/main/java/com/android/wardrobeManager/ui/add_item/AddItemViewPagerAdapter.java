package com.android.wardrobeManager.ui.add_item;

import com.android.wardrobeManager.database.ClothingItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AddItemViewPagerAdapter extends FragmentPagerAdapter {

    private ClothingItem clothingItem;

    private AddImageEditFragment imageEditFragment = null;
    private AdvEditFragment paramEdit = null;

    public AddItemViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

        imageEditFragment = new AddImageEditFragment();
        paramEdit = new AdvEditFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = imageEditFragment;
        } else {
            fragment = paramEdit;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    public AddImageEditFragment getImageEditFragment() {
        return imageEditFragment;
    }

}
