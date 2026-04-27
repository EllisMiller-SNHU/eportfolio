package com.zybooks.inventorymanagementsystem;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "InventoryManager.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_INVENTORY = "inventory";

    // User Table Columns
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";

    // Inventory Table Columns
    private static final String KEY_ITEM_ID = "id";
    private static final String KEY_ITEM_NAME = "name";
    private static final String KEY_ITEM_DESCRIPTION = "description";
    private static final String KEY_ITEM_QUANTITY = "quantity";
    private static final String KEY_ITEM_MIN_LEVEL = "min_level";
    private static final String KEY_ITEM_USER_ID = "user_id";

    // Create User Table
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + "(" +
                    KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_USERNAME + " TEXT UNIQUE," +
                    KEY_PASSWORD + " TEXT," +
                    KEY_EMAIL + " TEXT" +
                    ")";

    // Create Inventory Table
    private static final String CREATE_TABLE_INVENTORY =
            "CREATE TABLE " + TABLE_INVENTORY + "(" +
                    KEY_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_ITEM_NAME + " TEXT," +
                    KEY_ITEM_DESCRIPTION + " TEXT," +
                    KEY_ITEM_QUANTITY + " INTEGER," +
                    KEY_ITEM_MIN_LEVEL + " INTEGER," +
                    KEY_ITEM_USER_ID + " INTEGER," +
                    "FOREIGN KEY(" + KEY_ITEM_USER_ID + ") REFERENCES " +
                    TABLE_USERS + "(" + KEY_USER_ID + ")" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_INVENTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    // User CRUD operations

    /**
     * Add a new user to the database
     * @return true if user created successfully, false otherwise
     */
    public boolean addUser(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, username);
        values.put(KEY_PASSWORD, password); // In a real app, use password hashing
        values.put(KEY_EMAIL, email);

        // Insert row - returns row ID or -1 if error
        long id = db.insert(TABLE_USERS, null, values);
        db.close();

        return id != -1;
    }

    /**
     * Check if a user exists with the given username and password
     */
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { KEY_USER_ID };
        String selection = KEY_USERNAME + "=? AND " + KEY_PASSWORD + "=?";
        String[] selectionArgs = { username, password };

        Cursor cursor = db.query(
                TABLE_USERS,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    /**
     * Get user ID by username
     */
    @SuppressLint("Range")
    public int getUserIdByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { KEY_USER_ID };
        String selection = KEY_USERNAME + "=?";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(
                TABLE_USERS,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex(KEY_USER_ID));
        }

        cursor.close();
        db.close();

        return userId;
    }

    // Add these methods to your existing DatabaseHelper.java class

// Inventory CRUD operations

    /**
     * Add a new inventory item to the database
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long addInventoryItem(InventoryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_NAME, item.getName());
        values.put(KEY_ITEM_DESCRIPTION, item.getDescription());
        values.put(KEY_ITEM_QUANTITY, item.getQuantity());
        values.put(KEY_ITEM_MIN_LEVEL, item.getMinLevel());
        values.put(KEY_ITEM_USER_ID, item.getUserId());

        // Insert row - returns row ID or -1 if error
        long id = db.insert(TABLE_INVENTORY, null, values);
        db.close();

        return id;
    }

    /**
     * Get all inventory items for a specific user
     */
    @SuppressLint("Range")
    public List<InventoryItem> getInventoryItems(int userId) {
        List<InventoryItem> itemList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_INVENTORY +
                " WHERE " + KEY_ITEM_USER_ID + " = ?" +
                " ORDER BY " + KEY_ITEM_NAME + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userId)});

        // Loop through all rows and add to list
        if (cursor.moveToFirst()) {
            do {
                InventoryItem item = new InventoryItem();
                item.setId(cursor.getInt(cursor.getColumnIndex(KEY_ITEM_ID)));
                item.setName(cursor.getString(cursor.getColumnIndex(KEY_ITEM_NAME)));
                item.setDescription(cursor.getString(cursor.getColumnIndex(KEY_ITEM_DESCRIPTION)));
                item.setQuantity(cursor.getInt(cursor.getColumnIndex(KEY_ITEM_QUANTITY)));
                item.setMinLevel(cursor.getInt(cursor.getColumnIndex(KEY_ITEM_MIN_LEVEL)));
                item.setUserId(cursor.getInt(cursor.getColumnIndex(KEY_ITEM_USER_ID)));

                itemList.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return itemList;
    }

    /**
     * Update an inventory item
     * @return true if successful, false otherwise
     */
    public boolean updateInventoryItem(InventoryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_NAME, item.getName());
        values.put(KEY_ITEM_DESCRIPTION, item.getDescription());
        values.put(KEY_ITEM_QUANTITY, item.getQuantity());
        values.put(KEY_ITEM_MIN_LEVEL, item.getMinLevel());

        // Update row - returns number of rows affected
        int rowsAffected = db.update(TABLE_INVENTORY, values,
                KEY_ITEM_ID + " = ?",
                new String[]{String.valueOf(item.getId())});
        db.close();

        return rowsAffected > 0;
    }

    /**
     * Delete an inventory item
     * @return true if successful, false otherwise
     */
    public boolean deleteInventoryItem(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete row - returns number of rows affected
        int rowsAffected = db.delete(TABLE_INVENTORY,
                KEY_ITEM_ID + " = ?",
                new String[]{String.valueOf(itemId)});
        db.close();

        return rowsAffected > 0;
    }
}
