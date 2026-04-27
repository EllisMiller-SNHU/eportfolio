package com.zybooks.inventorymanagementsystem;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountDialog extends Dialog {

    private EditText usernameInput, passwordInput, emailInput;
    private Button createButton, cancelButton;
    private DatabaseHelper dbHelper;

    public CreateAccountDialog(Context context, DatabaseHelper dbHelper) {
        super(context);
        this.dbHelper = dbHelper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_create_account);

        // Find views
        usernameInput = findViewById(R.id.newUsernameInput);
        passwordInput = findViewById(R.id.newPasswordInput);
        emailInput = findViewById(R.id.emailInput);
        createButton = findViewById(R.id.createButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Set click listeners
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void createAccount() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();

        // Validate inputs
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(),
                    "Username and password are required",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Create new user in database
        boolean success = dbHelper.addUser(username, password, email);

        if (success) {
            Toast.makeText(getContext(),
                    "Account created successfully!",
                    Toast.LENGTH_SHORT).show();
            dismiss();
        } else {
            Toast.makeText(getContext(),
                    "Username already exists or database error",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
