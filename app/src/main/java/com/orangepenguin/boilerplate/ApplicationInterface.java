package com.orangepenguin.boilerplate;

public interface ApplicationInterface {
    void showMessage(String format, Object... params);
    void savePreference(String name, String value);
    void savePreference(String name, Boolean value);
    void clearPreference(String name);
    String getPreference(String name, String defaultValue);
}
