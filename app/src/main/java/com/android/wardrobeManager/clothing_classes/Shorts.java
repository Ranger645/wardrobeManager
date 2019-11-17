package com.android.wardrobeManager.clothing_classes;

import android.os.Parcel;

import com.android.wardrobeManager.ClothingItem;

public class Shorts extends ClothingItem {
    public Shorts(int color) {
        super(color);
    }

    public static final Creator<Shorts> CREATOR = new Creator<Shorts>() {
        @Override
        public Shorts createFromParcel(Parcel in) {
            return new Shorts(in);
        }

        @Override
        public Shorts[] newArray(int size) {
            return new Shorts[size];
        }
    };

    @Override
    public Class getClothingType() {
        return this.getClass();
    }

    @Override
    public String clothingTypeToString() {
        return "Shorts";
    }

    public Shorts(Parcel source){
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
