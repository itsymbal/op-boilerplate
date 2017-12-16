package com.orangepenguin.boilerplate;

import com.orangepenguin.boilerplate.di.DefaultComponentFactory;
import com.orangepenguin.boilerplate.di.Injector;
import com.squareup.leakcanary.LeakCanary;

public abstract class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        setUpLogging();

        Injector.setComponentFactory(new DefaultComponentFactory(this));
    }

    protected abstract void setUpLogging();
}
