package com.zybooks.inventorymanagementsystem;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class SmsUtil {

    private static final String TAG = "SmsUtil";

    /**
     * Send an SMS message if permission is granted
     *
     * @param context Application context
     * @param message Message to send
     */
    public static void sendSms(Context context, String message) {
        // Check if we have permission
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "SMS permission not granted");
            return;
        }

        try {
            // In a real app, you would get the phone number from user settings
            // For demo purposes, we'll just use a placeholder and log the message
            String phoneNumber = "5555555555"; // Placeholder

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);

            Toast.makeText(context, "SMS notification sent", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "SMS notification sent: " + message);
        } catch (Exception e) {
            Toast.makeText(context, "Failed to send SMS notification", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Failed to send SMS notification", e);
        }
    }
}
