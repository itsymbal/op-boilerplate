package com.orangepenguin.boilerplate;

public interface BaseViewInterface {

    void showMessage(String format, Object... params);

    void savePreference(String name, String value);

    void savePreference(String name, Boolean value);

    void clearPreference(String name);

    String getPreference(String name, String defaultValue);

    void showLoadingIndicator();

    void hideLoadingIndicator();
}
