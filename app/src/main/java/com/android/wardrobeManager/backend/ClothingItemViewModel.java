package com.android.wardrobeManager.backend;

import android.app.Application;
import android.util.Log;

import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.database.ClothingItemDatabaseRepository;
import com.android.wardrobeManager.ui.util.Utility;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class ClothingItemViewModel extends AndroidViewModel {

    private MutableLiveData<ClothingItem> clothingItem = null;
    private ClothingItemDatabaseRepository clothingItemDatabaseRepository;

    public ClothingItemViewModel(@NonNull Application application) {
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

    public void removeClothingItemColor(int color) {
        ClothingItem item = this.getClothingItem().getValue();
        int[] colors = Utility.parseClothingItemColors(item.getColors());
        if (colors.length <= 1)
            return;
        StringBuilder newColors = new StringBuilder();
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] != color) {
                newColors.append(Integer.toHexString(colors[i]));
                if (i < colors.length - 1)
                    newColors.append(",");
            }
        }
        item.setColors(newColors.toString());
        this.setClothingItem(item);
    }

    public void addClothingItemColor(int color) {
        ClothingItem item = this.getClothingItem().getValue();
        StringBuilder builder = new StringBuilder(item.getColors());
        if (builder.charAt(builder.length() - 1) != ',')
            builder.append(",");
        builder.append(Integer.toHexString(color));
        item.setColors(builder.toString());
        this.setClothingItem(item);
    }
}
