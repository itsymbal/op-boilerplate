package com.orangepenguin.boilerplate.singletons;

import com.orangepenguin.boilerplate.util.ProductionLogTree;

import timber.log.Timber;

public class BoilerplateApplication extends BaseApplication {
    @Override
    protected void setUpLogging() {
        // TODO: integrate Crashlytics
        // Crashlytics.start();
        Timber.plant(new ProductionLogTree());
    }
}
