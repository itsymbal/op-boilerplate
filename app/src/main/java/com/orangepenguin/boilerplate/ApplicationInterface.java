package com.orangepenguin.boilerplate;

/**
 * Preferences code is placed here, in the Application, instead of Activity for two reasons. One, it facilitates code
 * reuse -- can call this directly from Presenter, instead of calling the same method on the View, which would need
 * to be
 * implemented directly in BaseActivity if Activity is the view and in BaseFragment which would redirect to
 * BaseActivity.
 * Two - it's safer because all preferences are saved in the same file. Saving Preferences using Activity's
 * getPreferences() method can leave Fragment being unable to save / retrieve its preferences if it is hosted by two
 * different activities (e.g. detail fragment in master/detail flow in landscape / portrait configurations on 7' tablet)
 * <p>
 * More Preferences methods can be added as needed for different datatypes of preferences
 */
public interface ApplicationInterface {
    void showMessage(String format, Object... params);
    void savePreference(String name, String value);
    void savePreference(String name, Boolean value);
    void clearPreference(String name);
    String getPreference(String name, String defaultValue);
}
