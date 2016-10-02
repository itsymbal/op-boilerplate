package com.orangepenguin.boilerplate.repository;

import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.model.User;
import com.orangepenguin.boilerplate.rest.GitHubClient;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

public class UserRepository {

    @Inject GitHubClient gitHubClient;
    @Inject Scheduler observeScheduler;

    public UserRepository() {
        Injector.getRepositoryComponent().inject(this);
    }

    public Observable<User> fetchUser(String username) {
        //TODO: add code to fetch User from memory cache first, then network API - implement Strategy pattern?
        //TODO: add code to fetch User from local DB cache after memory cache before network API

        return Observable.fromCallable(() -> fetchUserSync(username))
                .observeOn(observeScheduler)
                .subscribeOn(Schedulers.io());
    }

    // This method can actually be private; set as public for the time being for ease of testing
    //    properly will be tested through fetchUser()
    public User fetchUserSync(String username) throws IOException {
        //        return gitHubClient
        //                .user(username)
        //                .observeOn(observeScheduler)
        //                .map(apiUser -> User.fromApiUser(apiUser).build());
        //        final ApiUser[] apiUser = new ApiUser[1];
        // this call is synchronous, intentionally.
        //        Call<ApiUser> call = gitHubClient.callUser(username);
        Call<ApiUser> call = gitHubClient.callUser(username);

        ApiUser apiUser = call.execute().body();
        //        ApiUser apiUser = call.execute().body();
        User user = User.fromApiUser(apiUser).build();
        return user;
    }
}
