package com.android.wardrobeManager.backend;

import android.app.Application;

import com.android.wardrobeManager.database.ClothingItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class AddItemViewModel extends AndroidViewModel {

    private MutableLiveData<ClothingItem> clothingItem = null;

    public AddItemViewModel(@NonNull Application application) {
        super(application);
        this.clothingItem = new MutableLiveData<ClothingItem>() {};
    }

    public void setClothingItem(ClothingItem item) {
        this.clothingItem.setValue(item);
    }

    public MutableLiveData<ClothingItem> getClothingItem() {
        return clothingItem;
    }
}
