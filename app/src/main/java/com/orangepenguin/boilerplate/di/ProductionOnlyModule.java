package com.orangepenguin.boilerplate.di;

import android.widget.ImageView;

import com.orangepenguin.boilerplate.util.FitCenterCropWhitePlaceholderFetcher;
import com.orangepenguin.boilerplate.util.ImageFetcher;

import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.orangepenguin.boilerplate.BuildConfig.GITHUB_URL;

@Module
public class ProductionOnlyModule {
    @Provides
    @Singleton
    @Named("serverUrl")
    protected AtomicReference<String> provideGitHubUrl() {
        return new AtomicReference<>(GITHUB_URL);
    }

    @Singleton
    @Provides
    @Named("fitCenterCropWhitePlaceholder")
    ImageFetcher<String, ImageView> provideImageFetcher() {
        return new FitCenterCropWhitePlaceholderFetcher();
    }
}
