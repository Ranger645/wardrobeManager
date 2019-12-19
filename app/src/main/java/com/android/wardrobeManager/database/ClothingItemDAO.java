package com.android.wardrobeManager.database;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ClothingItemDAO {

    @Insert
    void insert(ClothingItem item);

    @Update
    void update(ClothingItem item);

    @Delete
    void delete(ClothingItem item);

    @Query("DELETE FROM clothing_table")
    void deleteAllItems();

    @Query("SELECT * FROM clothing_table ORDER BY id DESC")
    LiveData<List<ClothingItem>> getAllItems();

}
