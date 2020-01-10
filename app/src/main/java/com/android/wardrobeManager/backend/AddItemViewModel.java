package com.android.wardrobeManager.backend;

import android.app.Application;
import android.util.Log;

import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.database.ClothingItemDatabaseRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class AddItemViewModel extends AndroidViewModel {

    private MutableLiveData<ClothingItem> clothingItem = null;
    private ClothingItemDatabaseRepository clothingItemDatabaseRepository;

    public AddItemViewModel(@NonNull Application application) {
        super(application);
        this.clothingItem = new MutableLiveData<ClothingItem>() {};

        this.clothingItemDatabaseRepository = new ClothingItemDatabaseRepository(application);
    }

    public void setClothingItem(ClothingItem item) {
        this.clothingItem.setValue(item);
    }

    public MutableLiveData<ClothingItem> getClothingItem() {
        return clothingItem;
    }

    public void persistToDatabase() {
        clothingItemDatabaseRepository.insert(clothingItem.getValue());
        Log.d("DATABASE_UPDATE", clothingItem.toString());
    }
}
