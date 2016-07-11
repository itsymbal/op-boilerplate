package com.orangepenguin.boilerplate.singletons;

import android.app.Application;

import com.orangepenguin.boilerplate.di.ApplicationComponent;
import com.orangepenguin.boilerplate.di.ApplicationModule;
import com.orangepenguin.boilerplate.di.DaggerApplicationComponent;
import com.orangepenguin.boilerplate.di.Injector;
import com.squareup.leakcanary.LeakCanary;

/**
 * TODO: http://blog.bradcampbell.nz/keep-your-main-thread-synchronous/
 */

public abstract class BaseApplication extends Application {

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
}