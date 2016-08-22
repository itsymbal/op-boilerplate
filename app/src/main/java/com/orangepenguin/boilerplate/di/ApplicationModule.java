package com.orangepenguin.boilerplate.di;

import android.content.Context;

import com.orangepenguin.boilerplate.singletons.ApplicationInterface;
import com.orangepenguin.boilerplate.singletons.BaseApplication;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the Context dependency to the
 * {@link ApplicationComponent}.
 */
@Module
public final class ApplicationModule {

    private final BaseApplication application;

    public ApplicationModule(BaseApplication application) {
        this.application = application;
    }

    @Provides
    Context provideContext() {
        return application;
    }

    @Provides
    ApplicationInterface provideApplicationInterface() {
        return application;
    }
}