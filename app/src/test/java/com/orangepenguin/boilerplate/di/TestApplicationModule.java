package com.orangepenguin.boilerplate.di;

import android.widget.ImageView;

import com.orangepenguin.boilerplate.rest.GitHubClientBuilder;
import com.orangepenguin.boilerplate.rx.RxSchedulers;
import com.orangepenguin.boilerplate.rx.RxTestSchedulers;
import com.orangepenguin.boilerplate.util.ImageFetcher;
import com.orangepenguin.boilerplate.util.NotificationUtiConsoleImpl;
import com.orangepenguin.boilerplate.util.NotificationUtil;
import com.orangepenguin.boilerplate.util.SharedPreferenceUtilInMemoryImpl;
import com.orangepenguin.boilerplate.util.SharedPreferencesUtil;

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

    @Provides
    @Singleton
    RxSchedulers provideSchedulers() {
        return new RxTestSchedulers();
    }

    @Provides
    @Singleton
    GitHubClientBuilder.GitHubClient provideGitHubClient() {
        return mock(GitHubClientBuilder.GitHubClient.class);
    }

    @Provides
    @Singleton
    public SharedPreferencesUtil provideSharedPreferencesUtil() {
        return new SharedPreferenceUtilInMemoryImpl();
    }

    @Provides
    @Singleton
    public NotificationUtil provideNotificationUtil() {
        return new NotificationUtiConsoleImpl();
    }

    @Singleton
    @Provides
    @Named("fitCenterCropWhitePlaceholder")
    ImageFetcher<String, ImageView> provideImageLoader() {
        return mock(ImageFetcher.class);
    }
}
