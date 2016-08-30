package com.orangepenguin.boilerplate.di;

import com.orangepenguin.boilerplate.rest.GitHubClient;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
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

            gitHubClient = new Retrofit.Builder()
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(GITHUB_URL)
                    .build()
                    .create(GitHubClient.class);
        }
        return gitHubClient;
    }
}
