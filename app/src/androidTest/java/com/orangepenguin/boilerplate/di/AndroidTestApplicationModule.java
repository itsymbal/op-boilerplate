package com.orangepenguin.boilerplate.di;

import android.content.Context;

import com.orangepenguin.boilerplate.Application;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the Context dependency to the
 * {@link ApplicationComponent}.
 */
@Module
public final class AndroidTestApplicationModule {
    private final Application application;

    public AndroidTestApplicationModule(Application application) {
        this.application = application;
    }

    /**
     * Supply Application context. It's important to realize not all Contexts are equal so only use
     * this where Application is appropriate https://possiblemobile.com/2013/06/context/
     */
    @Provides
    Context provideContext() {
        return application;
    }
}
