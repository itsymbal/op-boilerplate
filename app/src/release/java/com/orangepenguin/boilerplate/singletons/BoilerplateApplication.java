package com.orangepenguin.boilerplate.singletons;

import com.crashlytics.android.Crashlytics;
import com.orangepenguin.boilerplate.BaseApplication;
import com.orangepenguin.boilerplate.util.ProductionLogTree;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class BoilerplateApplication extends BaseApplication {
    @Override
    protected void setUpLogging() {
        Fabric.with(this, new Crashlytics());
        Timber.plant(new ProductionLogTree());
    }
}
