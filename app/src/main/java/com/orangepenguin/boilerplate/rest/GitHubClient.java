package com.orangepenguin.boilerplate.rest;

import com.orangepenguin.boilerplate.repository.ApiUser;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Retrofit interface to GitHub API methods
 */
public interface GitHubClient {
    @GET("/users/{username}")
    Observable<ApiUser> user(@Path("username") String username);

    //    @GET("/gists/{id}")
    //    Observable<GistDetail> gist(@Path("id") String id);
}

