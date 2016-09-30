package com.orangepenguin.boilerplate.di;

import dagger.Module;
import rx.Scheduler;
import rx.schedulers.Schedulers;

@Module
public final class TestRepositoryModule extends RepositoryModule {
    String gitHubUrl;

    @Override
    protected String getGitHubUrl() {
        return gitHubUrl;
    }

    @Override
    protected Scheduler getObserveScheduler() {
        return Schedulers.immediate();
    }

    public void setGitHubUrl(String gitHubUrl) {
        this.gitHubUrl = gitHubUrl;
    }
}