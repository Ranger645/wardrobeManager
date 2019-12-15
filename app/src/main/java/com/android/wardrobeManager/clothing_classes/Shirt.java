package com.android.wardrobeManager.clothing_classes;

import android.os.Parcel;
import com.android.wardrobeManager.ClothingItem;


public class Shirt extends ClothingItem {
    public Shirt(int color) {
        super(color);
    }

    public Shirt() {super();}

    public static final Creator<Shirt> CREATOR = new Creator<Shirt>() {
        @Override
        public Shirt createFromParcel(Parcel in) {
            return new Shirt(in);
        }

        @Override
        public Shirt[] newArray(int size) {
            Shirt[] arr = new Shirt[size];
            for (int i = 0; i < size; i++) {
                arr[i] = new Shirt();
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
        return "Shirt";
    }

    public Shirt(Parcel source){
        super(source);

    }

}
