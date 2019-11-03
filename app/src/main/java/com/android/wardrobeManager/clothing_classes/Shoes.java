package com.android.wardrobeManager.clothing_classes;

import com.android.wardrobeManager.ClothingItem;

public class Shoes extends ClothingItem {


    public Shoes(int color) {
        super(color);
    }


    @Override
    public Class getClothingType() {
        return Shoes.class;
    }
}
