package com.android.wardrobeManager.ui.closet;

import android.widget.ImageButton;

import com.android.wardrobeManager.database.ClothingItem;

public interface ClosetClothingItemClickListener {

    void onClothingItemClick(ClothingItem item, ImageButton clothingImageView);

}
