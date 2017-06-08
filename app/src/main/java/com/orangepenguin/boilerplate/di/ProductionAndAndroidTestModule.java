package com.orangepenguin.boilerplate.di;

import android.content.Context;
import android.widget.ImageView;

import com.orangepenguin.boilerplate.rest.GitHubClientBuilder;
import com.orangepenguin.boilerplate.rx.AndroidRxSchedulers;
import com.orangepenguin.boilerplate.rx.RxSchedulers;
import com.orangepenguin.boilerplate.util.AndroidNotificationUtil;
import com.orangepenguin.boilerplate.util.AndroidSharedPreferenceUtil;
import com.orangepenguin.boilerplate.util.FitCenterCropWhitePlaceholderFetcher;
import com.orangepenguin.boilerplate.util.ImageFetcher;
import com.orangepenguin.boilerplate.util.NotificationUtil;
import com.orangepenguin.boilerplate.util.SharedPreferencesUtil;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
@Singleton
public class ProductionAndAndroidTestModule {

    @Singleton
    @Provides
    @Named("fitCenterCropWhitePlaceholder")
    ImageFetcher<String, ImageView> provideImageFetcher() {
        return new FitCenterCropWhitePlaceholderFetcher();
    }

    @Provides
    @Singleton
    RxSchedulers provideSchedulers() {
        return new AndroidRxSchedulers();
    }

    @Provides
    @Singleton
    SharedPreferencesUtil provideSharedPreferenceUtil(Context context) {
        return new AndroidSharedPreferenceUtil(context);
    }

    @Provides
    @Singleton
    NotificationUtil provideNotificationUtil(Context context) {
        return new AndroidNotificationUtil(context);
    }

    @Provides
    @Singleton
    public GitHubClientBuilder.GitHubClient provideGitHubClient(@Named("serverUrl") String serverUrl) {
        return GitHubClientBuilder.getGitHubClient(serverUrl);
    }
}
