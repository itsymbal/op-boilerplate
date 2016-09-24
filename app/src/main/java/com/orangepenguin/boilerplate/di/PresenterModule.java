package com.orangepenguin.boilerplate.di;

import com.google.gson.GsonBuilder;
import com.orangepenguin.boilerplate.repository.AutoValueAdapterFactory;
import com.orangepenguin.boilerplate.rest.GitHubClient;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.orangepenguin.boilerplate.BuildConfig.GITHUB_URL;

@Module
final class PresenterModule {

    GitHubClient gitHubClient;

    @Provides
    public GitHubClient provideGitHubClient() {
        if (gitHubClient == null) {

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();
            GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(
                    new GsonBuilder()
                            .registerTypeAdapterFactory(new AutoValueAdapterFactory())
                            .create());

            gitHubClient = new Retrofit.Builder()
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .addConverterFactory(gsonConverterFactory)
                    .baseUrl(GITHUB_URL)
                    .build()
                    .create(GitHubClient.class);
        }
        return gitHubClient;
    }

    /*
     * In production code, observe on Main thread. In test code, we don't have no Main thread (because it depends on
     * an Android looper), so test component can supply a different type of scheduler
     */
    @Provides
    public Scheduler provideObserveOnScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
