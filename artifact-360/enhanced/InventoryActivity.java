package com.zybooks.inventorymanagementsystem;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class InventoryActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 100;

    private AppDatabase db;
    private InventoryDao inventoryDao;

    private InventoryAdapter adapter;
    private RecyclerView inventoryGrid;
    private TextView emptyView;
    private SearchView searchView;

    private List<InventoryItem> inventoryItems;

    private String currentUsername;
    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        // Room DB
        db = AppDatabase.getInstance(this);
        inventoryDao = db.inventoryDao();

        // User info
        currentUsername = UserPreferences.getUsername(this);

        // FIX: you need a real userId lookup (DO NOT parse username)
        currentUserId = Integer.parseInt(UserPreferences.getUsername(this));

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Inventory Management");

        // Views
        inventoryGrid = findViewById(R.id.inventoryGrid);
        emptyView = findViewById(R.id.emptyView);
        searchView = findViewById(R.id.searchView);
        FloatingActionButton addItemFab = findViewById(R.id.addItemFab);

        inventoryGrid.setLayoutManager(new GridLayoutManager(this, 1));

        loadInventoryItems();

        // Search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchItems(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterItems(newText);
                return true;
            }
        });

        addItemFab.setOnClickListener(v -> showAddItemDialog());

        requestSmsPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadInventoryItems();
    }

    private void loadInventoryItems() {
        inventoryItems = inventoryDao.getAllItems(currentUserId);

        adapter = new InventoryAdapter(this, inventoryItems);
        inventoryGrid.setAdapter(adapter);

        adapter.setOnItemClickListener(new InventoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(InventoryItem item) {
                showEditItemDialog(item);
            }

            @Override
            public void onDeleteClick(InventoryItem item) {
                confirmDelete(item);
            }
        });

        if (inventoryItems.isEmpty()) {
            inventoryGrid.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            inventoryGrid.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            checkLowStockItems();
        }
    }

    private void searchItems(String query) {
        inventoryItems = inventoryDao.searchItems(currentUserId, query);
        adapter.updateList(inventoryItems);
    }

    private void filterItems(String query) {
        List<InventoryItem> filtered = new ArrayList<>();

        for (InventoryItem item : inventoryItems) {
            if (item.getName().toLowerCase().contains(query.toLowerCase())
                    || item.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(item);
            }
        }

        adapter.updateList(filtered);

        if (filtered.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            inventoryGrid.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            inventoryGrid.setVisibility(View.VISIBLE);
        }
    }

    private void showAddItemDialog() {
        ItemDialogFragment dialog = ItemDialogFragment.newInstance(null, currentUserId);
        dialog.show(getSupportFragmentManager(), "AddItem");
    }

    private void showEditItemDialog(InventoryItem item) {
        ItemDialogFragment dialog = ItemDialogFragment.newInstance(item, currentUserId);
        dialog.show(getSupportFragmentManager(), "EditItem");
    }

    private void confirmDelete(InventoryItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete " + item.getName() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    inventoryDao.deleteItem(item);
                    loadInventoryItems();
                    Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void checkLowStockItems() {
        List<InventoryItem> lowStock = new ArrayList<>();

        for (InventoryItem item : inventoryItems) {
            if (item.getQuantity() <= item.getMinLevel()) {
                lowStock.add(item);
            }
        }

        if (!lowStock.isEmpty() && hasSmsPermission()) {
            StringBuilder msg = new StringBuilder("Low stock alert:\n");

            for (InventoryItem item : lowStock) {
                msg.append("- ")
                        .append(item.getName())
                        .append(": ")
                        .append(item.getQuantity())
                        .append("\n");
            }

            SmsUtil.sendSms(this, msg.toString());
        }
    }

    private void requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    SMS_PERMISSION_CODE);
        }
    }

    private boolean hasSmsPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inventory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_logout) {
            UserPreferences.clearUserData(this);

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}