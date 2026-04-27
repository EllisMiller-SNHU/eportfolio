package com.zybooks.inventorymanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private Button loginButton, createAccountButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Find views by ID
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        createAccountButton = findViewById(R.id.createAccountButton);

        // Set click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                // Validate inputs
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this,
                            "Please enter both username and password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check credentials against database
                if (dbHelper.checkUser(username, password)) {
                    // Successful login
                    Toast.makeText(LoginActivity.this,
                            "Login successful!",
                            Toast.LENGTH_SHORT).show();

                    // Store current user in preferences
                    UserPreferences.saveUsername(LoginActivity.this, username);

                    // Navigate to inventory screen
                    Intent intent = new Intent(LoginActivity.this, InventoryActivity.class);
                    startActivity(intent);
                    finish(); // Close login activity
                } else {
                    // Failed login
                    Toast.makeText(LoginActivity.this,
                            "Invalid username or password",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listener for create account button
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateAccountDialog();
            }
        });
    }

    // Show dialog to create a new account
    private void showCreateAccountDialog() {
        CreateAccountDialog dialog = new CreateAccountDialog(this, dbHelper);
        dialog.show();
    }
}
