package com.orangepenguin.boilerplate.rest

import com.orangepenguin.boilerplate.repository.ApiMoshiAdapterFactory
import com.orangepenguin.boilerplate.repository.ApiUser
import com.squareup.moshi.Moshi

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable
import rx.schedulers.Schedulers

object GitHubClientBuilder {

    fun getGitHubClient(serverUrl: String): GitHubClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        val moshi = Moshi.Builder()
                .add(ApiMoshiAdapterFactory.create())
                .build()

        return Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(serverUrl)
                .build()
                .create(GitHubClient::class.java)
    }

    /**
     * Retrofit interface to GitHub API methods
     */
    interface GitHubClient {
        @GET("/users/{username}")
        fun user(@Path("username") username: String): Observable<ApiUser>

        @GET("/users/{username}")
        fun callUser(@Path("username") username: String): Call<ApiUser>

        //    @GET("/gists/{id}")
        //    Observable<GistDetail> gist(@Path("id") String id);
    }
}
