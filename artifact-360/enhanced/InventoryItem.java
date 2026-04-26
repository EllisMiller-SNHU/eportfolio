package com.zybooks.inventorymanagementsystem;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "inventory")
public class InventoryItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String description;
    private int quantity;
    private int minLevel;
    private int userId;

    public InventoryItem() {
        // Default constructor
    }

    public InventoryItem(int id, String name, String description, int quantity, int minLevel, int userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.minLevel = minLevel;
        this.userId = userId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isLowStock() {
        return quantity <= minLevel;
    }
}