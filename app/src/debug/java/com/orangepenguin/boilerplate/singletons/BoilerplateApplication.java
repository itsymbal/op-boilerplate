package com.orangepenguin.boilerplate.singletons;

import com.orangepenguin.boilerplate.Application;

import timber.log.Timber;

public class BoilerplateApplication extends Application {
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
