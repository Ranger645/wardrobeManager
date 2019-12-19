package com.android.wardrobeManager.database;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "clothing_table")
public class ClothingItem {

    public ClothingItem() {
    }

    public ClothingItem(String type) {
        this.type = type;
    }

    public ClothingItem(String type, String colors, String material, String brand, float cost, String season) {
        this.type = type;
        this.colors = colors;
        this.material = material;
        this.brand = brand;
        this.cost = cost;
        this.season = season;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String type;

    private String colors;

    private String material;

    private String brand;

    private float cost;

    private String season;

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) { this.type = type; }

    public void setColors(String colors) { this.colors = colors; }

    public void setMaterial(String material) { this.material = material; }

    public void setBrand(String brand) { this.brand = brand; }

    public void setCost(float cost) { this.cost = cost; }

    public void setSeason(String season) { this.season = season; }

    public int getId() { return id; }

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
}
