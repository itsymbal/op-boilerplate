package com.orangepenguin.boilerplate.rest;

import android.support.annotation.NonNull;

import com.orangepenguin.boilerplate.repository.ApiMoshiAdapterFactory;
import com.orangepenguin.boilerplate.repository.ApiUser;
import com.squareup.moshi.Moshi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.schedulers.Schedulers;

public final class GitHubClientBuilder {
    private GitHubClientBuilder() {}

    public static GitHubClient getGitHubClient(@NonNull String serverUrl) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Moshi moshi = new Moshi.Builder()
                .add(ApiMoshiAdapterFactory.create())
                .build();

        return new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(serverUrl)
                .build()
                .create(GitHubClient.class);
    }

    /**
     * Retrofit interface to GitHub API methods
     */
    public interface GitHubClient {
        @GET("/users/{username}")
        Observable<ApiUser> user(@Path("username") String username);

        @GET("/users/{username}")
        Call<ApiUser> callUser(@Path("username") String username);

        //    @GET("/gists/{id}")
        //    Observable<GistDetail> gist(@Path("id") String id);
    }
}
