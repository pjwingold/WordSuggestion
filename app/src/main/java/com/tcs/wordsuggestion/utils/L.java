package com.tcs.wordsuggestion.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Log debug class
 *
 */
public class L {
    private static String TAG = "TCS";
    private static boolean DEBUG = true;

    public static void log(String msg) {
        if (DEBUG) Log.i(TAG, msg);
    }

    public static void debug(String msg) {
        if (DEBUG) Log.d(TAG, msg);
    }

    public static void error(String msg, @Nullable Exception e) {
        if (DEBUG) Log.e(TAG, msg, e);
    }

    public static void wtf(String msg) {
        if (DEBUG) Log.wtf(TAG, msg);
    }

    public static void toastLong(String msg, Context c) {
        Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
    }
}
