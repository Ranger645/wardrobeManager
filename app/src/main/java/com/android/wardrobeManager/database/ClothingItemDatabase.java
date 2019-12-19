package com.android.wardrobeManager.database;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {ClothingItem.class}, version = 1)
public abstract class ClothingItemDatabase extends RoomDatabase {

    private static ClothingItemDatabase instance;

    public abstract ClothingItemDAO clothingItemDao();

    public static synchronized ClothingItemDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ClothingItemDatabase.class, "clothing_item_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Called when the database is created
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private ClothingItemDAO dao;

        private PopulateDbAsyncTask(ClothingItemDatabase db) {
            this.dao = db.clothingItemDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.insert(new ClothingItem("shirt", "red", "performance", "nike", 50, "summer"));
            dao.insert(new ClothingItem("shorts", "red", "performance", "nike", 50, "summer"));
            // dao.insert(new ClothingItem("pants", "red", "performance", "nike", 75, "summer"));
            return null;
        }
    }

}