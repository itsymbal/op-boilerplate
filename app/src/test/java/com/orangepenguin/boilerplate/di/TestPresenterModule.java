package com.orangepenguin.boilerplate.di;

import com.google.common.annotations.VisibleForTesting;
import com.orangepenguin.boilerplate.ApplicationInterface;
import com.orangepenguin.boilerplate.rest.GitHubClient;

import dagger.Module;
import dagger.Provides;

@Module
public final class TestPresenterModule {
    private ApplicationInterface application;
    private GitHubClient gitHubClient;

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

    @VisibleForTesting
    public static class Builder {
        private ApplicationInterface application;
        private GitHubClient gitHubClient;

        public Builder baseApplication(ApplicationInterface application) {
            this.application = application;
            return this;
        }

        public Builder gitHubClient(GitHubClient gitHubClient) {
            this.gitHubClient = gitHubClient;
            return this;
        }

        public TestPresenterModule build() {
            TestPresenterModule presenterModule = new TestPresenterModule();
            presenterModule.application = application;
            presenterModule.gitHubClient = gitHubClient;
            return presenterModule;
        }
    }
}