package com.orangepenguin.boilerplate.di;

import android.widget.ImageView;

import com.orangepenguin.boilerplate.util.ImageFetcher;

import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class AndroidTestOnlyModule {

    // using AtomicReference allows setting its contents later
    public AtomicReference<String> gitHubUrl = new AtomicReference<>();

    @Provides
    @Singleton
    @Named("serverUrl")
    protected AtomicReference<String> provideGitHubUrl() {
        return gitHubUrl;
    }

    @Singleton
    @Provides
    @Named("fitCenterCropWhitePlaceholder")
    ImageFetcher<String, ImageView> provideImageLoader() {
        return mock(ImageFetcher.class);
    }
}
