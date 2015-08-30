package com.tcs.wordsuggestion.common;

import android.app.Application;

/**
 * Base Application
 */
public class TCSApplication extends Application {
    private static TCSApplication mApp;

    //cache the source data for look up
    private static String sourceData = "";

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static TCSApplication getInstance() {
        return mApp;
    }

    public static void setSource(String newSource) {
        sourceData = newSource;
    }

    public static String getSource() {
        return sourceData;
    }
}
