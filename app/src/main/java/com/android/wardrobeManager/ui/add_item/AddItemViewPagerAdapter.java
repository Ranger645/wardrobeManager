package com.android.wardrobeManager.ui.add_item;

import android.os.Bundle;

import com.android.wardrobeManager.database.ClothingItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.MutableLiveData;

public class AddItemViewPagerAdapter extends FragmentPagerAdapter {

    private ClothingItem clothingItem;

    private ItemPreviewFragment preview = null;
    private ItemParamEditFragment paramEdit = null;

    public AddItemViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

        preview = new ItemPreviewFragment();
        paramEdit = new ItemParamEditFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = preview;
        } else {
            fragment = paramEdit;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    public ItemPreviewFragment getPreviewFragment() {
        return preview;
    }
}
