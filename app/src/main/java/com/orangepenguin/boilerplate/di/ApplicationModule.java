package com.orangepenguin.boilerplate.di;

import android.content.Context;

import com.orangepenguin.boilerplate.Application;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.orangepenguin.boilerplate.BuildConfig.GITHUB_URL;

/**
 * This is a Dagger module. We use this to pass in the Context dependency to the
 * {@link ApplicationComponent}.
 */
@Module
public final class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
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

    @Provides
    @Singleton
    @Named("serverUrl")
    protected String provideGitHubUrl() {
        return GITHUB_URL;
    }
}
