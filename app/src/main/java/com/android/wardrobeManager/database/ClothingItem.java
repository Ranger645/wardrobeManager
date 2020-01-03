package com.android.wardrobeManager.database;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "clothing_table")
public class ClothingItem implements Parcelable{

    public ClothingItem() {
        this.customImage = false;
        this.type = "t_shirt";
        this.colors = "FF0000FF";
        this.design = "PRIMARY_SECONDARY";
        this.style = 0;
        this.material = "cotton";
        this.brand = "nike";
        this.cost = 0.0f;
        this.size = "big";
    }

    public ClothingItem(boolean customImage, String type, String subType, String colors, String design, int style, String material, String brand, float cost, String size) {
        this("", customImage, type, subType, colors, design, style, material, brand, cost, size);
    }

    public ClothingItem(String customName, boolean customImage, String type, String subType, String colors, String design, int style, String material, String brand, float cost, String size) {
        this.customName = customName;
        this.customImage = customImage;
        this.type = type;
        this.subType = subType;
        this.colors = colors;
        this.design = design;
        this.style = style;
        this.material = material;
        this.brand = brand;
        this.cost = cost;
        this.size = size;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private boolean customImage;

    private String customName;

    private String type;

    private String subType;

    private String colors;

    /*
     * The type of pattern that the colors take. Examples are given as a list in strings.xml
     */
    private String design;

    /*
     * 0 is least formal, 99 is most formal
     */
    private int style;

    private String material;

    private String brand;

    private float cost;

    /*
     * Typically big, small, or perfect
     */
    private String size;

    public int getId() {
        return id;
    }

    public boolean isCustomImage() {
        return customImage;
    }

    public String getCustomName() {
        return customName;
    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public String getColors() {
        return colors;
    }

    public String getDesign() {
        return design;
    }

    public int getStyle() {
        return style;
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

    public String getSize() {
        return size;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomImage(boolean customImage) {
        this.customImage = customImage;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(id);
        String[] paramArray = new String[] {
            customName, Boolean.toString(customImage), type, subType, colors, design, Integer.toString(style),
                material, brand, Float.toString(cost), size
        };
        for (String param : paramArray) {
            builder.append(",");
            builder.append(param);
        }
        return builder.toString();
    }

    public ClothingItem(Parcel in) {
        id = in.readInt();
        customImage = in.readInt() == 1;
        customName = in.readString();
        type = in.readString();
        subType = in.readString();
        colors = in.readString();
        design = in.readString();
        style = in.readInt();
        material = in.readString();
        brand = in.readString();
        cost = in.readFloat();
        size = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeInt(customImage ? 1 : 0);
        out.writeString(customName);
        out.writeString(type);
        out.writeString(subType);
        out.writeString(colors);
        out.writeString(design);
        out.writeInt(style);
        out.writeString(material);
        out.writeString(brand);
        out.writeFloat(cost);
        out.writeString(size);
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
