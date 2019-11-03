package com.android.wardrobeManager.clothing_classes;

import com.android.wardrobeManager.ClothingItem;

public class Shirt extends ClothingItem {
    public Shirt(int color) {
        super(color);
    }

    @Override
    public Class getClothingType() {
        return Shirt.class;
    }
}
