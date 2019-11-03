package com.android.wardrobeManager;

public abstract class ClothingItem {

    private int color;
    private static int idCount = 0;
    private int ClothingItemId;

    public ClothingItem(int color) {
        this.color = color;
        this.ClothingItemId = idCount;
        idCount++;
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
        return ClothingItemId;
    }

    public abstract Class getClothingType();

}
