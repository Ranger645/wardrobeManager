package com.android.wardrobeManager.backend;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.SparseArray;

import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.database.ClothingItemDatabaseRepository;
import com.android.wardrobeManager.ui.images.ClothingItemImageManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class ClothingItemDatabaseViewModel extends AndroidViewModel {

    private ClothingItemDatabaseRepository repository;
    private LiveData<List<ClothingItem>> clothingItems;

    public ClothingItemDatabaseViewModel(@NonNull final Application application) {
        super(application);
        repository = new ClothingItemDatabaseRepository(application);
        clothingItems = repository.getClothingItems();

    }

    public void insert(ClothingItem item) {
        repository.insert(item);
    }

    public LiveData<List<ClothingItem>> getClothingItems() {
        return clothingItems;
    }
}
