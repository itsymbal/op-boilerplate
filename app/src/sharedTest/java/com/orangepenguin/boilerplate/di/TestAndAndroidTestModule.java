package com.orangepenguin.boilerplate.di;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class TestAndAndroidTestModule {

    //TODO: figure out a way to set this from the test. Actually this can be moved to AndroidTest only module
    public String gitHubUrl = "YOMAMA";

    @Provides
    @Singleton
    @Named("serverUrl")
    protected String provideGitHubUrl() {
        return gitHubUrl;
    }
}
