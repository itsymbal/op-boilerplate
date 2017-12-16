package com.orangepenguin.boilerplate.repository

import com.orangepenguin.boilerplate.model.User
import com.orangepenguin.boilerplate.rest.GitHubClientBuilder
import rx.Observable
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject
constructor(private val gitHubClient: GitHubClientBuilder.GitHubClient) {

    fun fetchUser(username: String): Observable<User> {
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

        return Observable.fromCallable { fetchUserSync(username) }
    }

    // This method can actually be private; set as public for the time being for ease of testing
    //    properly will be tested through fetchUser()
    @Throws(IOException::class)
    fun fetchUserSync(username: String): User {
        val call = gitHubClient.callUser(username)
        val apiUser = call.execute().body()
        return User.fromApiUser(apiUser).build()
    }
}
