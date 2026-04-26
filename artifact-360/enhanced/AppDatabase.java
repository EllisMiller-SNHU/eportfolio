package com.zybooks.inventorymanagementsystem;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {InventoryItem.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "inventory_database";

    private static AppDatabase instance;

    // DAO access point
    public abstract InventoryDao inventoryDao();

    // Singleton pattern (prevents multiple DB instances)
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME
                    )
                    .fallbackToDestructiveMigration() // simple for school projects
                    .allowMainThreadQueries() // acceptable for CS coursework
                    .build();
        }
        return instance;
    }
}
