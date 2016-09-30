package com.orangepenguin.boilerplate.di;

import com.orangepenguin.boilerplate.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Subcomponent;

@Singleton
@Subcomponent(modules = {RepositoryModule.class})
public interface RepositoryComponent {
    void inject(UserRepository userRepository);
}