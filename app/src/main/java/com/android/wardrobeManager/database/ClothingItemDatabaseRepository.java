package com.android.wardrobeManager.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.android.wardrobeManager.database.ClothingItem;
import com.android.wardrobeManager.database.ClothingItemDAO;
import com.android.wardrobeManager.database.ClothingItemDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ClothingItemDatabaseRepository {

    private ClothingItemDAO dao;
    private LiveData<List<ClothingItem>> clothingItems;

    public ClothingItemDatabaseRepository(Application application) {
        // Initial copy of database item live data object to local reference
        ClothingItemDatabase db = ClothingItemDatabase.getInstance(application);
        this.dao = db.clothingItemDao();
        this.clothingItems = this.dao.getAllItems();
    }

    public void insert(ClothingItem item) {
        new InsertItemAsyncTask(dao).execute(item);
    }

    public void update(ClothingItem item) { new UpdateItemAsyncTask(dao).execute(item); }

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

    private static class UpdateItemAsyncTask extends AsyncTask<ClothingItem, Void, Void> {

        private ClothingItemDAO dao;

        private UpdateItemAsyncTask(ClothingItemDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ClothingItem... items) {
            this.dao.update(items[0]);
            return null;
        }
    }

}
