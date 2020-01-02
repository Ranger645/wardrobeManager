package com.android.wardrobeManager.backend;

import android.app.Application;

import com.android.wardrobeManager.database.ClothingItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class AddItemViewModel extends AndroidViewModel {

    private ClothingItem clothingItem = null;

    public AddItemViewModel(@NonNull Application application) {
        super(application);
    }

    public void setClothingItem(ClothingItem item) {
        clothingItem = item;
    }

    public ClothingItem getClothingItem() {
        return clothingItem;
    }
}
