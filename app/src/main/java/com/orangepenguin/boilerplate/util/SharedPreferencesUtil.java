package com.orangepenguin.boilerplate.util;

public interface SharedPreferencesUtil {

    String getPreference(String key, String defaultValue);
    void savePreference(String key, String value);
    void clearPreference(String key);
}
