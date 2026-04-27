package com.zybooks.inventorymanagementsystem;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {

    private static final String PREF_NAME = "InventoryAppPrefs";
    private static final String KEY_USERNAME = "username";

    /**
     * Save the current user's username
     */
    public static void saveUsername(Context context, String username) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    /**
     * Get the currently logged in username
     */
    public static String getUsername(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_USERNAME, null);
    }

    /**
     * Clear all user preferences (logout)
     */
    public static void clearUserData(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }
}
