package com.zybooks.inventorymanagementsystem;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

    private DatabaseHelper dbHelper;
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

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Get current user info
        currentUsername = UserPreferences.getUsername(this);
        currentUserId = dbHelper.getUserIdByUsername(currentUsername);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);

        toolbar.setTitle("Inventory Management");

        // Find views
        inventoryGrid = findViewById(R.id.inventoryGrid);
        emptyView = findViewById(R.id.emptyView);
        androidx.appcompat.widget.SearchView searchView = findViewById(R.id.searchView);
        FloatingActionButton addItemFab = findViewById(R.id.addItemFab);

        // Set up RecyclerView
        inventoryGrid.setLayoutManager(new GridLayoutManager(this, 1));

        // Load inventory items
        loadInventoryItems();

        // Set up search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterItems(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterItems(newText);
                return true;
            }
        });

        // Set up FAB click listener
        addItemFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemDialog();
            }
        });

        // Request SMS permission
        requestSmsPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload inventory when returning to this activity
        loadInventoryItems();
    }

    private void loadInventoryItems() {
        // Get items from database
        inventoryItems = dbHelper.getInventoryItems(currentUserId);

        // Set up adapter
        adapter = new InventoryAdapter(this, inventoryItems);
        inventoryGrid.setAdapter(adapter);

        // Set item click listener
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

        // Show empty view if no items
        if (inventoryItems.isEmpty()) {
            inventoryGrid.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            inventoryGrid.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);

            // Check for low stock items and send notifications
            checkLowStockItems();
        }
    }

    private void filterItems(String query) {
        // Filter inventory items by name or description
        List<InventoryItem> filteredList = new ArrayList<>();
        for (InventoryItem item : inventoryItems) {
            if (item.getName().toLowerCase().contains(query.toLowerCase()) ||
                    item.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.updateList(filteredList);

        // Show empty view if no results
        if (filteredList.isEmpty()) {
            inventoryGrid.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            emptyView.setText("No matching items found");
        } else {
            inventoryGrid.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
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

    private void confirmDelete(final InventoryItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete " + item.getName() + "?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Delete item from database
                        dbHelper.deleteInventoryItem(item.getId());

                        // Refresh inventory list
                        loadInventoryItems();

                        Toast.makeText(InventoryActivity.this,
                                "Item deleted",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void checkLowStockItems() {
        List<InventoryItem> lowStockItems = new ArrayList<>();

        for (InventoryItem item : inventoryItems) {
            if (item.getQuantity() <= item.getMinLevel()) {
                lowStockItems.add(item);
            }
        }

        if (!lowStockItems.isEmpty() && hasSmsPermission()) {
            // Send SMS notifications for low stock items
            StringBuilder message = new StringBuilder();
            message.append("Low stock alert:\n");

            for (InventoryItem item : lowStockItems) {
                message.append("- ").append(item.getName())
                        .append(": ").append(item.getQuantity())
                        .append(" (Min: ").append(item.getMinLevel())
                        .append(")\n");
            }

            SmsUtil.sendSms(this, message.toString());
        }
    }

    private void requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Show explanation if needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                new AlertDialog.Builder(this)
                        .setTitle("SMS Permission Needed")
                        .setMessage("This app needs to send SMS messages to alert you about low stock items")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(InventoryActivity.this,
                                        new String[]{Manifest.permission.SEND_SMS},
                                        SMS_PERMISSION_CODE);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create()
                        .show();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        SMS_PERMISSION_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Toast.makeText(this, "SMS notifications enabled", Toast.LENGTH_SHORT).show();

                // Check for low stock items now that we have permission
                checkLowStockItems();
            } else {
                // Permission denied
                Toast.makeText(this,
                        "SMS notifications disabled. App will continue without this feature.",
                        Toast.LENGTH_LONG).show();
            }
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
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            // Log out user
            UserPreferences.clearUserData(this);
        }
        return false;
    }
}