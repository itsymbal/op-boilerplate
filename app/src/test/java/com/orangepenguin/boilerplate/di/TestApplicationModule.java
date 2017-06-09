package com.orangepenguin.boilerplate.di;

import android.widget.ImageView;

import com.orangepenguin.boilerplate.util.ImageFetcher;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

/**
 * This is a Dagger module. We use this to pass Application scoped dependencies to the {@link
 * UnitTestApplicationComponent}.
 */
@Module
public final class TestApplicationModule {

    @Singleton
    @Provides
    @Named("fitCenterCropWhitePlaceholder")
    ImageFetcher<String, ImageView> provideImageLoader() {
        return mock(ImageFetcher.class);
    }
}
