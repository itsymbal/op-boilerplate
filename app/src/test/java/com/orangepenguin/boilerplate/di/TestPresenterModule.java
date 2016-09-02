package com.orangepenguin.boilerplate.di;

import com.google.common.annotations.VisibleForTesting;
import com.orangepenguin.boilerplate.ApplicationInterface;
import com.orangepenguin.boilerplate.rest.GitHubClient;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public final class TestPresenterModule {
    private ApplicationInterface application;
    private GitHubClient gitHubClient;
    private Scheduler observeOnScheduler;

    private TestPresenterModule() {
    }

    public static Builder builder() {
        return new Builder();
    }

    @Provides
    ApplicationInterface provideApplication() {
        return application;
    }

    @Provides
    GitHubClient provideGitHubClient() {
        return gitHubClient;
    }

    @Provides
    Scheduler provideObserveOnScheduler() {
        return observeOnScheduler;
    }

    @VisibleForTesting
    public static class Builder {
        private ApplicationInterface application;
        private GitHubClient gitHubClient;
        private Scheduler observeOnScheduler;

        public Builder baseApplication(ApplicationInterface application) {
            this.application = application;
            return this;
        }

        public Builder gitHubClient(GitHubClient gitHubClient) {
            this.gitHubClient = gitHubClient;
            return this;
        }

        public Builder observeOnScheduler(Scheduler observeOnScheduler) {
            this.observeOnScheduler = observeOnScheduler;
            return this;
        }

        public TestPresenterModule build() {
            TestPresenterModule presenterModule = new TestPresenterModule();
            presenterModule.application = application;
            presenterModule.gitHubClient = gitHubClient;
            presenterModule.observeOnScheduler = observeOnScheduler;

            return presenterModule;
        }
    }
}