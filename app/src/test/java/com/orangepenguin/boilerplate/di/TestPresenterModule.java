package com.orangepenguin.boilerplate.di;

import com.orangepenguin.boilerplate.ApplicationInterface;
import com.orangepenguin.boilerplate.repository.UserRepository;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public final class TestPresenterModule {
    private ApplicationInterface application = mock(ApplicationInterface.class);
    private UserRepository userRepository = mock(UserRepository.class);

    @Provides
    public ApplicationInterface provideApplication() {
        return application;
    }

    @Provides
    public UserRepository provideUserRepo() {
        return userRepository;
    }
}