package com.orangepenguin.boilerplate.usecase

import com.orangepenguin.boilerplate.model.User
import com.orangepenguin.boilerplate.repository.UserRepository
import rx.Observable
import javax.inject.Inject

open class UserUseCase @Inject constructor(private val userRepository: UserRepository) {

    open fun fetchUser(username: String): Observable<User> {
        return userRepository.fetchUser(username)
    }
}
