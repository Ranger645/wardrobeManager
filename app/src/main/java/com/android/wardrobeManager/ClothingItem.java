package com.android.wardrobeManager;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class ClothingItem implements Parcelable {

    protected int color;
    private static int idCount = 0;
    protected int clothingItemId;

    public ClothingItem(int color) {
        this.color = color;
        this.clothingItemId = idCount;
        idCount++;
    }

    public ClothingItem(Parcel source) {
        color = source.readInt();
        clothingItemId = source.readInt();
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public static int getIdCount() {
        return idCount;
    }

    public int getId() {
        return clothingItemId;
    }

    public abstract Class getClothingType();



}
