package com.android.wardrobeManager.database;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "clothing_table")
public class ClothingItem implements Parcelable{

    public ClothingItem() {
        this.customImage = false;
        this.type = "shirt";
        this.colors = "FF0000FF";
        this.colorStyle = "PRIMARY_SECONDARY";
        this.material = "cotton";
        this.brand = "nike";
        this.cost = 0.0f;
        this.season = "summer";
    }

    public ClothingItem(String type) {
        this.type = type;
    }

    public ClothingItem(boolean customImage, String type, String colors, String colorStyle, String material, String brand, float cost, String season) {
        this.customImage = customImage;
        this.type = type;
        this.colors = colors;
        this.colorStyle = colorStyle;
        this.material = material;
        this.brand = brand;
        this.cost = cost;
        this.season = season;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private boolean customImage;

    private String type;

    private String colors;

    /*
     * The type of pattern that the colors take. Examples are given as a list in strings.xml
     */
    private String colorStyle;

    private String material;

    private String brand;

    private float cost;

    private String season;

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomImage(boolean customImage) {
        this.customImage = customImage;
    }

    public void setType(String type) { this.type = type; }

    public void setColors(String colors) { this.colors = colors; }

    public void setMaterial(String material) { this.material = material; }

    public void setBrand(String brand) { this.brand = brand; }

    public void setCost(float cost) { this.cost = cost; }

    public void setSeason(String season) { this.season = season; }

    public void setColorStyle(String colorStyle) {
        this.colorStyle = colorStyle;
    }

    public String getColorStyle() {
        return colorStyle;
    }

    public int getId() { return id; }

    public boolean getCustomImage() {
        return customImage;
    }

    public String getType() {
        return type;
    }

    public String getColors() {
        return colors;
    }

    public String getMaterial() {
        return material;
    }

    public String getBrand() {
        return brand;
    }

    public float getCost() {
        return cost;
    }

    public String getSeason() {
        return season;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(id);
        builder.append(customImage);
        builder.append(type);
        builder.append(colors);
        builder.append(colorStyle);
        builder.append(material);
        builder.append(brand);
        builder.append(cost);
        builder.append(season);
        return builder.toString();
    }

    public ClothingItem(Parcel in) {
        id = in.readInt();
        customImage = in.readInt() == 1;
        type = in.readString();
        colors = in.readString();
        colorStyle = in.readString();
        brand = in.readString();
        cost = in.readFloat();
        season = in.readString();
        material = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeInt(customImage ? 1 : 0);
        out.writeString(type);
        out.writeString(colors);
        out.writeString(colorStyle);
        out.writeString(brand);
        out.writeFloat(cost);
        out.writeString(season);
        out.writeString(material);
    }

    public static final Parcelable.Creator<ClothingItem> CREATOR = new Parcelable.Creator<ClothingItem>()
    {
        public ClothingItem createFromParcel(Parcel in)
        {
            return new ClothingItem(in);
        }
        public ClothingItem[] newArray(int size)
        {
            return new ClothingItem[size];
        }
    };
}
