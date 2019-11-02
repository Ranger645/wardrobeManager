package com.android.wardrobeManager;

public class ClothingItem {

    int color;
    String clothingType;

    public ClothingItem(int color, String clothingType) {
        this.color = color;
        this.clothingType = clothingType;
    }

    public void SetColor(int color) {
        this.color = color;
    }

    public void SetClothingType(String clothingType) {
        this.clothingType = clothingType;
    }

    public int GetColor() {
        return color;
    }

    public String GetClothingType() {
        return clothingType;
    }


}
