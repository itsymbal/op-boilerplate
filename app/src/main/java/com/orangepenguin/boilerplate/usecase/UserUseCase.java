package com.orangepenguin.boilerplate.usecase;

import com.orangepenguin.boilerplate.model.User;
import com.orangepenguin.boilerplate.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

public class UserUseCase {

    private final UserRepository userRepository;

    @Inject
    public UserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Observable<User> fetchUser(String username) {
        return userRepository.fetchUser(username);
    }
}
