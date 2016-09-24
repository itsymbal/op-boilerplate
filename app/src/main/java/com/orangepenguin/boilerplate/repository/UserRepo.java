package com.orangepenguin.boilerplate.repository;

import com.orangepenguin.boilerplate.model.User;
import com.orangepenguin.boilerplate.rest.GitHubClient;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;

import static rx.Observable.just;

public class UserRepo {

    private GitHubClient gitHubClient;
    private Scheduler observeScheduler;

    @Inject
    public UserRepo(GitHubClient gitHubClient, Scheduler observeScheduler) {
        this.gitHubClient = gitHubClient;
        this.observeScheduler = observeScheduler;
    }

    public Observable<User> fetchUser(String username) {
        //TODO: add code to fetch ApiUser from API backend, convert to User
        //TODO: add code to fetch User from memory cache first, then network API
        //TODO: add code to fetch User from local DB cache
        // for now, just return a fake User object
        return Observable.just(User
                .builder()
                .login("itsymbal")
                .company("Orange Penguin")
                .name("Ilya Tsymbal")
                .avatarUrl("https://avatars.githubusercontent.com/u/1466908?v=3")
                .reposUrl("https://api.github.com/users/itsymbal/repos")
                .build()
        );
        //        return Observable.fromAsync(fetchUserAsync(username));
        //        return Observable.defer(fetchUserAsync(username));
    }

    public Observable<User> fetchUserAsync(String username) {
        //        return gitHubClient
        //                .user(username)
        //                .observeOn(observeScheduler)
        //                .map(apiUser -> User.fromApiUser(apiUser).build());
        final ApiUser[] apiUser = new ApiUser[1];
        gitHubClient
                .user(username)
                //                .observeOn(observeScheduler)
                .toBlocking()
                .subscribe(apiUser1 -> apiUser[0] = apiUser1);

        User user = User.fromApiUser(apiUser[0]).build();
        return just(user);
    }
}
