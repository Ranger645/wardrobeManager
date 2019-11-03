package com.android.wardrobeManager.clothing_classes;

import com.android.wardrobeManager.ClothingItem;

public class Shorts extends ClothingItem {
    public Shorts(int color) {
        super(color);
    }

    @Override
    public Class getClothingType() {
        return Shorts.class;
    }
}
