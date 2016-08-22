package com.orangepenguin.boilerplate.di;

import com.orangepenguin.boilerplate.singletons.ApplicationInterface;

import dagger.Module;
import dagger.Provides;

@Module
public final class TestPresenterModule {
    private ApplicationInterface application;

    private TestPresenterModule() {
    }

    public static Builder builder() {
        return new Builder();
    }

    @Provides
        //    @Nullable
    ApplicationInterface provideApplication() {
        return application;
    }

    public static class Builder {
        private ApplicationInterface application;


        public Builder baseApplication(ApplicationInterface application) {
            this.application = application;
            return this;
        }

        public TestPresenterModule build() {
            TestPresenterModule presenterModule = new TestPresenterModule();
            presenterModule.application = application;
            return presenterModule;
        }
    }
}