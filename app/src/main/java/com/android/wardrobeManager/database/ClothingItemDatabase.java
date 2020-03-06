package com.android.wardrobeManager.database;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {ClothingItem.class}, version = ClothingItemDatabase.VERSION)
public abstract class ClothingItemDatabase extends RoomDatabase {

    public static final int VERSION = 19;

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
//        @Override
//        public void onOpen(@NonNull SupportSQLiteDatabase db) {
//            super.onOpen(db);
//            new PopulateDbAsyncTask(instance).execute();
//        }

        @Override
        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
            super.onDestructiveMigration(db);
            new PopulateDbAsyncTask(instance).execute();
        }

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
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
            dao.insert(new ClothingItem(false, "top", "short_sleeve_shirt", "FFFFFFFF,FFFF0000,FFFFFF00,FF00FF00,FF00FFFF,FF0000FF,FFFF00FF", "diagonal_down_right", 0, "polyester", "nike", 50f, "big"));
            return null;
        }
    }

}
