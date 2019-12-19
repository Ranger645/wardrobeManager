package com.android.wardrobeManager.backend;

import android.app.Application;
import android.os.AsyncTask;

import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.database.ClothingItemDAO;
import com.android.wardrobeManager.database.ClothingItemDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ClothingItemRepository {

    private ClothingItemDAO dao;
    private LiveData<List<ClothingItem>> clothingItems;

    public ClothingItemRepository(Application application) {
        // Initial copy of database item live data object to local reference
        ClothingItemDatabase db = ClothingItemDatabase.getInstance(application);
        this.dao = db.clothingItemDao();
        this.clothingItems = this.dao.getAllItems();
    }

    public void insert(ClothingItem item) {
        new InsertItemAsyncTask(dao).execute(item);
    }

    public LiveData<List<ClothingItem>> getClothingItems() {
        return this.clothingItems;
    }


    private static class InsertItemAsyncTask extends AsyncTask<ClothingItem, Void, Void> {

        private ClothingItemDAO dao;

        private InsertItemAsyncTask(ClothingItemDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClothingItem... items) {
            this.dao.insert(items[0]);
            return null;
        }
    }

}
