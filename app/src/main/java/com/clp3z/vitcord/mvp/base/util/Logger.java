package com.clp3z.vitcord.mvp.base.util;

import android.util.Log;

import com.clp3z.vitcord.mvp.App;

/**
 * Created by Clelia López on 6/5/16
 */
public class Logger {

    /**
     * Attributes
     */
    private final String APP = "Vitcord-";
    private final String separator = "=====> ";
    private String TAG;


    public Logger(String tag) {
        TAG = tag;
    }

    public void log(String method, String message) {
        if (!App.isOnProduction())
            Log.i(APP + TAG, separator + method + ": " + message);
    }

    public void logDebug(String method, String message) {
        if (!App.isOnProduction())
            Log.d(APP + TAG, separator + method + ": " + message);
    }

    public void logError(String method, String message) {
        if (!App.isOnProduction())
            Log.e(APP + TAG, separator + method + ": " + message);
    }
}