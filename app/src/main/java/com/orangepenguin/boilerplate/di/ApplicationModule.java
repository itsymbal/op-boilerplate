package com.orangepenguin.boilerplate.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orangepenguin.boilerplate.Application;
import com.orangepenguin.boilerplate.rest.GitHubClientBuilder;
import com.orangepenguin.boilerplate.rx.AndroidRxSchedulers;
import com.orangepenguin.boilerplate.rx.RxSchedulers;
import com.orangepenguin.boilerplate.util.AndroidNotificationUtil;
import com.orangepenguin.boilerplate.util.AndroidSharedPreferenceUtil;
import com.orangepenguin.boilerplate.util.NotificationUtil;
import com.orangepenguin.boilerplate.util.PermissionUtil;
import com.orangepenguin.boilerplate.util.SharedPreferencesUtil;

import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the Context dependency to the
 * {@link ApplicationComponent}.
 */
@Module
public final class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    /**
     * Supply Application context. It's important to realize not all Contexts are equal so only use
     * this where Application is appropriate https://possiblemobile.com/2013/06/context/
     */
    @Provides
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    NotificationUtil provideNotificationUtil(Context context) {
        return new AndroidNotificationUtil(context);
    }

    @Provides
    @Singleton
    PermissionUtil providePermissionUtil(SharedPreferencesUtil sharedPreferencesUtil, Context context) {
        return new PermissionUtil(sharedPreferencesUtil, context);
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
    public GitHubClientBuilder.GitHubClient provideGitHubClient(@NonNull @Named("serverUrl") AtomicReference<String>
                                                                        serverUrl) {
        return GitHubClientBuilder.INSTANCE.getGitHubClient(serverUrl.get());
    }

}
