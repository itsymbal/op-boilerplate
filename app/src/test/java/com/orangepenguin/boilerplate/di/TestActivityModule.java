package com.orangepenguin.boilerplate.di;

import com.google.common.annotations.VisibleForTesting;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

@Module
public final class TestActivityModule {
    private Picasso picasso;

    public static Builder builder() {
        return new Builder();
    }

    @Provides
    Picasso providePicasso() {
        return picasso;
    }

    @VisibleForTesting
    public static class Builder {
        private Picasso picasso;

        public Builder picasso(Picasso picasso) {
            this.picasso = picasso;
            return this;
        }

        public TestActivityModule build() {
            TestActivityModule activityModule = new TestActivityModule();
            activityModule.picasso = picasso;

            return activityModule;
        }
    }
}