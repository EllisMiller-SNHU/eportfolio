package com.zybooks.inventorymanagementsystem;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ItemDialogFragment extends DialogFragment {

    private static final String ARG_ITEM = "item";
    private static final String ARG_USER_ID = "user_id";

    private EditText nameInput, descriptionInput;
    private NumberPicker quantityPicker, minLevelPicker;

    private AppDatabase db;
    private InventoryDao inventoryDao;

    private InventoryItem item;
    private int userId;

    public static ItemDialogFragment newInstance(InventoryItem item, int userId) {
        ItemDialogFragment fragment = new ItemDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        if (item != null) {
            args.putSerializable(ARG_ITEM, item);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = AppDatabase.getInstance(requireContext());
        inventoryDao = db.inventoryDao();

        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_USER_ID);

            if (getArguments().containsKey(ARG_ITEM)) {
                item = (InventoryItem) getArguments().getSerializable(ARG_ITEM);
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        View view = requireActivity()
                .getLayoutInflater()
                .inflate(R.layout.dialog_item, null);

        nameInput = view.findViewById(R.id.itemNameInput);
        descriptionInput = view.findViewById(R.id.itemDescriptionInput);
        quantityPicker = view.findViewById(R.id.quantityPicker);
        minLevelPicker = view.findViewById(R.id.minLevelPicker);

        quantityPicker.setMinValue(0);
        quantityPicker.setMaxValue(1000);

        minLevelPicker.setMinValue(0);
        minLevelPicker.setMaxValue(100);

        if (item != null) {
            nameInput.setText(item.getName());
            descriptionInput.setText(item.getDescription());
            quantityPicker.setValue(item.getQuantity());
            minLevelPicker.setValue(item.getMinLevel());

            builder.setTitle("Edit Item");
        } else {
            builder.setTitle("Add New Item");
        }

        builder.setView(view)
                .setPositiveButton(item != null ? "Update" : "Add", (dialog, which) -> saveItem())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        return builder.create();
    }

    private void saveItem() {
        String name = nameInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();
        int quantity = quantityPicker.getValue();
        int minLevel = minLevelPicker.getValue();

        if (name.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (item != null) {
            // UPDATE
            item.setName(name);
            item.setDescription(description);
            item.setQuantity(quantity);
            item.setMinLevel(minLevel);

            inventoryDao.updateItem(item);

            Toast.makeText(getContext(), "Item updated", Toast.LENGTH_SHORT).show();

        } else {
            // INSERT
            InventoryItem newItem = new InventoryItem();
            newItem.setName(name);
            newItem.setDescription(description);
            newItem.setQuantity(quantity);
            newItem.setMinLevel(minLevel);
            newItem.setUserId(userId);

            inventoryDao.insertItem(newItem);

            Toast.makeText(getContext(), "Item added", Toast.LENGTH_SHORT).show();
        }

        // refresh UI
        if (getActivity() instanceof InventoryActivity) {
            ((InventoryActivity) getActivity()).recreate();
        }
    }
}