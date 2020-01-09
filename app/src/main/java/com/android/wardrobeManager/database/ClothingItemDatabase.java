package com.android.wardrobeManager.database;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {ClothingItem.class}, version = ClothingItemDatabase.VERSION)
public abstract class ClothingItemDatabase extends RoomDatabase {

    public static final int VERSION = 13;

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
            new PopulateDbAsyncTask(instance).execute();
        }

//        public void onOpen(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new PopulateDbAsyncTask(instance).execute();
//        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private ClothingItemDAO dao;

        private PopulateDbAsyncTask(ClothingItemDatabase db) {
            this.dao = db.clothingItemDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.insert(new ClothingItem(false, "top", "short_sleeve_shirt",
                    "FFFF0000", "primary_secondary", 0, "polyester",
                    "nike", 50f, "big"));
            dao.insert(new ClothingItem(false, "bottom", "shorts",
                    "FFFF00FF,FF00FFFF", "primary_secondary", 10, "cotton",
                    "adidas", 60f, "perfect"));
            return null;
        }
    }

}
