package com.orangepenguin.boilerplate.util;

import android.content.Context;

public class AndroidSharedPreferenceUtil implements SharedPreferencesUtil {

    private static final String FILE_NAME = "APPLICATION_PREFERENCES";
    private final Context context;

    public AndroidSharedPreferenceUtil(Context context) {
        this.context = context;
    }

    @Override
    public String getPreference(String key, String defaultValue) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                .getString(key, defaultValue);
    }

    @Override
    public void savePreference(String key, String value) {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }

    @Override
    public void clearPreference(String key) {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().remove(key).apply();
    }
}
