package com.orangepenguin.boilerplate.di;

import com.orangepenguin.boilerplate.repository.UserRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    UserRepository userRepository;

    @Provides
    public UserRepository provideUserRepository() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return new UserRepository();
    }
}
