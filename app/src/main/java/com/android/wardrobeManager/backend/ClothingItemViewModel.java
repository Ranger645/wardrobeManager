package com.android.wardrobeManager.backend;

import android.app.Application;
import android.os.Parcelable;

import com.android.wardrobeManager.database.ClothingItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ClothingItemViewModel extends AndroidViewModel {

    private ClothingItemRepository repository;
    private LiveData<List<ClothingItem>> clothingItems;

    public ClothingItemViewModel(@NonNull Application application) {
        super(application);
        repository = new ClothingItemRepository(application);
        clothingItems = repository.getClothingItems();
    }

    public void insert(ClothingItem item) {
        repository.insert(item);
    }

    public LiveData<List<ClothingItem>> getClothingItems() {
        return clothingItems;
    }

}
