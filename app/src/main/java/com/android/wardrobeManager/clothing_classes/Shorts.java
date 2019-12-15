package com.android.wardrobeManager.clothing_classes;

import android.os.Parcel;

import com.android.wardrobeManager.ClothingItem;

public class Shorts extends ClothingItem {

    public Shorts(int color) {
        super(color);
    }
    public Shorts() {super();}

    public static final Creator<Shorts> CREATOR = new Creator<Shorts>() {
        @Override
        public Shorts createFromParcel(Parcel in) {
            return new Shorts(in);
        }

        @Override
        public Shorts[] newArray(int size) {
            Shorts[] arr = new Shorts[size];
            for (int i = 0; i < size; i++) {
                arr[i] = new Shorts();
            }
            return arr;
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

}
