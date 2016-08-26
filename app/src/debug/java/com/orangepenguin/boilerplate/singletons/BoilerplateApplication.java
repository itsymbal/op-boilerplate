package com.orangepenguin.boilerplate.singletons;

import com.orangepenguin.boilerplate.BaseApplication;

import timber.log.Timber;

public class BoilerplateApplication extends BaseApplication {
    @Override
    protected void setUpLogging() {
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }
        });
    }
}
