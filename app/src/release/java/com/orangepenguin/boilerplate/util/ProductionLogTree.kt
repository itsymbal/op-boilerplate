package com.orangepenguin.boilerplate.util

import android.annotation.SuppressLint
import android.util.Log

import com.crashlytics.android.Crashlytics

import timber.log.Timber

import android.util.Log.ERROR
import android.util.Log.INFO
import android.util.Log.WARN

class ProductionLogTree : Timber.Tree() {
    override protected fun isLoggable(priority: Int): Boolean {
        return priority > INFO
    }

    @SuppressLint(value = "LogNotTimber")
    override protected fun log(priority: Int, tag: String, message: String, throwable: Throwable?) {

        Crashlytics.log(priority, tag, message)

        if (throwable != null) {
            if (priority == ERROR) {
                Log.e(tag, message, throwable)
                Crashlytics.logException(throwable)
            } else if (priority == WARN) {
                Log.w(tag, message)
            }
        }
    }
}
