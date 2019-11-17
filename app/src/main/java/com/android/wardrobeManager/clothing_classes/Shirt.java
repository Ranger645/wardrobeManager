package com.android.wardrobeManager.clothing_classes;

import android.os.Parcel;

import com.android.wardrobeManager.ClothingItem;

public class Shirt extends ClothingItem {
    public Shirt(int color) {
        super(color);
    }

    public static final Creator<Shirt> CREATOR = new Creator<Shirt>() {
        @Override
        public Shirt createFromParcel(Parcel in) {
            return new Shirt(in);
        }

        @Override
        public Shirt[] newArray(int size) {
            return new Shirt[size];
        }
    };

    @Override
    public Class getClothingType() {
        return this.getClass();
    }

    @Override
    public String clothingTypeToString() {
        return "Shirt";
    }

    public Shirt(Parcel source){
        super(source);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(color);
        parcel.writeInt(clothingItemId);
    }
}
