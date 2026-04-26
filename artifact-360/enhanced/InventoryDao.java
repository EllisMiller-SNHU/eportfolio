package com.zybooks.inventorymanagementsystem;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface InventoryDao {

    @Insert
    void insertItem(InventoryItem item);

    @Update
    void updateItem(InventoryItem item);

    @Delete
    void deleteItem(InventoryItem item);

    @Query("SELECT * FROM inventory WHERE userId = :userId ORDER BY name ASC")
    List<InventoryItem> getAllItems(int userId);

    @Query("SELECT * FROM inventory WHERE userId = :userId AND " +
            "(name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%')")
    List<InventoryItem> searchItems(int userId, String query);
}
