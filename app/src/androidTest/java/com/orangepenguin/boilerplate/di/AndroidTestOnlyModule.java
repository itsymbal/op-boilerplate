package com.orangepenguin.boilerplate.di;

import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidTestOnlyModule {

    public AtomicReference<String> gitHubUrl = new AtomicReference<>();

    @Provides
    @Singleton
    @Named("serverUrl")
    protected AtomicReference<String> provideGitHubUrl() {
        return gitHubUrl;
    }
}
