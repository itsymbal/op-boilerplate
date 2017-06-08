package com.orangepenguin.boilerplate.repository;

import com.orangepenguin.boilerplate.model.User;
import com.orangepenguin.boilerplate.rest.GitHubClientBuilder;
import com.orangepenguin.boilerplate.rx.RxSchedulers;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import rx.Observable;

public class UserRepository {

    private final GitHubClientBuilder.GitHubClient gitHubClient;
    private final RxSchedulers schedulers; // Main thread on device; immediate in test

    @Inject
    public UserRepository(GitHubClientBuilder.GitHubClient gitHubClient, RxSchedulers schedulers) {
        this.gitHubClient = gitHubClient;
        this.schedulers = schedulers;
    }

    public Observable<User> fetchUser(String username) {
        //TODO: add code to fetch User from memory cache first, then network API - implement Strategy pattern?
        //TODO: add code to fetch User from local DB cache after memory cache before network API

        // This method could have easily used an Observable returned by Retrofit, transformed it and returned it like so
        //        return gitHubClient
        //                .user(username)
        //                .observeOn(observeScheduler)
        //                .map(apiUser -> User.fromApiUser(apiUser).build());
        // however, for illustrative purposes, we're going to create one from scratch. We will not always have the
        // luxury of being able to take an existing one.

        // fromCallable() takes a Callable, wraps it in an Observable, and defers calling it until someone calls
        // subscribe() on the observable
        return Observable.fromCallable(() -> fetchUserSync(username))
                //TODO: threading - apply the correct one from schedulers
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread());
    }

    // This method can actually be private; set as public for the time being for ease of testing
    //    properly will be tested through fetchUser()
    public User fetchUserSync(String username) throws IOException {

        Call<ApiUser> call = gitHubClient.callUser(username);
        ApiUser apiUser = call.execute().body();
        User user = User.fromApiUser(apiUser).build();
        return user;
    }
}
