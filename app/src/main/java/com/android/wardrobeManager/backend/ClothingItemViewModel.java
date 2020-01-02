package com.android.wardrobeManager.backend;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.SparseArray;

import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.ui.images.ClothingItemImageManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class ClothingItemViewModel extends AndroidViewModel {

    private ClothingItemRepository repository;
    private LiveData<List<ClothingItem>> clothingItems;
    private Observer<List<ClothingItem>> viewModelObserver;

    private SparseArray<Bitmap> clothingItemBitmaps;

    public ClothingItemViewModel(@NonNull final Application application) {
        super(application);
        repository = new ClothingItemRepository(application);
        clothingItems = repository.getClothingItems();

        clothingItemBitmaps = new SparseArray<>();

        clothingItems.observeForever(viewModelObserver = new Observer<List<ClothingItem>>() {
            @Override
            public void onChanged(List<ClothingItem> clothingItems) {
                for (ClothingItem item : clothingItems) {
                    if (clothingItemBitmaps.get(ClothingItemImageManager.getImageHash(item)) == null) {
                        clothingItemBitmaps.put(ClothingItemImageManager.getImageHash(item), ClothingItemImageManager.dynamicClothingItemLoad(application, item));
                    }
                }
            }
        });
    }

    public void insert(ClothingItem item) {
        repository.insert(item);
    }

    public LiveData<List<ClothingItem>> getClothingItems() {
        return clothingItems;
    }

    public SparseArray<Bitmap> getClothingItemBitmaps() {
        return clothingItemBitmaps;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        clothingItems.removeObserver(viewModelObserver);
    }
}
