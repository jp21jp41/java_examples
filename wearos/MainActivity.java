package com.example.myapplication.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.NodeClient;
import android.content.SharedPreferences;
import android.content.Context;

public class MainActivity extends Activity {
    private static final String WatchApp = "192.168.1.235";
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This is where you'll initialize your "Moat"
        checkAuthorizedNodes();

        Log.d(TAG, "Watch App Initialized and Secured.");
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Check if we've already done this
        SharedPreferences prefs = getSharedPreferences("ExclusivityMoat", MODE_PRIVATE);
        boolean alreadyChecked = prefs.getBoolean("init_check", false);

        if (!alreadyChecked) {
            // 2. Perform your one-time ID/Security check
            checkAuthorizedNodes();;

            // 3. Mark it as "Done" so it never runs again
            prefs.edit().putBoolean("init_check", true).apply();
            Log.d(WatchApp, "Initial check complete. Logic locked.");
        } else {
            Log.d(WatchApp, "Check already performed. Standing by to save battery.");
            // This is where the app stays "Idling"
        }
    }


    private void checkAuthorizedNodes() {
        // We will use this to verify only YOUR laptop is connected
        NodeClient nodeClient = Wearable.getNodeClient(this);
        // Logic for Node ID verification goes here
    }
}
