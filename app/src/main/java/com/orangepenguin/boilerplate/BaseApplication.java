package com.orangepenguin.boilerplate;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.orangepenguin.boilerplate.di.ApplicationComponent;
import com.orangepenguin.boilerplate.di.ApplicationModule;
import com.orangepenguin.boilerplate.di.DaggerApplicationComponent;
import com.orangepenguin.boilerplate.di.Injector;
import com.squareup.leakcanary.LeakCanary;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * TODO: http://blog.bradcampbell.nz/keep-your-main-thread-synchronous/
 */

public abstract class BaseApplication extends Application implements ApplicationInterface {

    private static BaseApplication application;

    public static BaseApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        LeakCanary.install(this);
        setUpLogging();

        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().applicationModule(new
                ApplicationModule
                (getApplication())).build();

        Injector.setApplicationComponent(applicationComponent);
    }

    protected abstract void setUpLogging();

    @Override
    public void showMessage(String format, Object... params) {
        Toast.makeText(this, String.format(format, params), LENGTH_SHORT).show();
    }

    @Override
    public void savePreference(String key, String value) {
        getEditor().putString(key, value).apply();
    }

    @Override
    public void savePreference(String key, Boolean value) {
        getEditor().putBoolean(key, value).apply();
    }

    @Override
    public void clearPreference(String key) {
        getEditor().remove(key).apply();
    }

    @Override
    public String getPreference(String key, String defaultValue) {
        return getSharedPreferences().getString(key, defaultValue);
    }

    private SharedPreferences.Editor getEditor() {
        return getSharedPreferences().edit();
    }

    private SharedPreferences getSharedPreferences() {
        return getSharedPreferences(getPackageName(),
                MODE_PRIVATE);
    }
}