package com.android.wardrobeManager;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

public abstract class ClothingItem extends AppCompatActivity implements Parcelable {

    protected int color;
    protected int clothingItemId;

    private static int idCount = 0;

    public ClothingItem(int color) {
        this.color = color;
        this.clothingItemId = idCount;
        idCount++;
    }

    public ClothingItem() {
        this.color = getResources().getColor(R.color.red);
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

    public abstract String clothingTypeToString();

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(color);
        parcel.writeInt(clothingItemId);
    }

    public int describeContents() {
        return 0;
    }

}
