package com.android.wardrobeManager.clothing_classes;

import android.os.Parcel;

import com.android.wardrobeManager.ClothingItem;

public class Shoes extends ClothingItem {
    public Shoes(int color) {
        super(color);
    }

    public static final Creator<Shoes> CREATOR = new Creator<Shoes>() {
        @Override
        public Shoes createFromParcel(Parcel in) {
            return new Shoes(in);
        }

        @Override
        public Shoes[] newArray(int size) {
            return new Shoes[size];
        }
    };

    @Override
    public Class getClothingType() {
        return null;
    }

    public Shoes(Parcel source){
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
