package com.orangepenguin.boilerplate.util;

import android.annotation.SuppressLint;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import timber.log.Timber;

import static android.util.Log.ERROR;
import static android.util.Log.INFO;
import static android.util.Log.WARN;

public class ProductionLogTree extends Timber.Tree {
    @Override
    protected boolean isLoggable(int priority) {
        return priority > INFO;
    }

    @SuppressLint(value = "LogNotTimber")
    @Override
    protected void log(int priority, String tag, String message, Throwable throwable) {

        if (!isLoggable(priority)) return;

        Crashlytics.log(priority, tag, message);

        if (throwable != null) {
            if (priority == ERROR) {
                Log.e(tag, message, throwable);
                Crashlytics.logException(throwable);
            } else if (priority == WARN) {
                Log.w(tag, message);
            }
        }
    }
}
