package com.orangepenguin.boilerplate.util;

import android.annotation.SuppressLint;
import android.util.Log;

import timber.log.Timber;

import static android.util.Log.INFO;

public class ProductionLogTree extends Timber.Tree {
    @Override
    protected boolean isLoggable(int priority) {
        return priority > INFO;
    }

    @SuppressLint(value = "LogNotTimber")
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {

        if (!isLoggable(priority)) return;

        // TODO: Crashlytics.log...

        if (t != null) {
            if (priority == Log.ERROR) {
                Log.e(tag, message, t);
                // TODO: Crashlytics.log (error.....
            } else if (priority == Log.WARN) {
                Log.w(tag, message);

                // TODO: Crashlytics.log (warn.......

            }
        }
    }
}
