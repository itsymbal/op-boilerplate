package com.orangepenguin.boilerplate.di;

import com.orangepenguin.boilerplate.screens.userdetails.UserDetailsPresenter;
import com.orangepenguin.boilerplate.screens.username.UsernamePresenter;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public final class TestPresenterModule {

    @Provides
    public UserDetailsPresenter provideUserDetailsPresenter() {
        return mock(UserDetailsPresenter.class);
    }

    @Provides
    public UsernamePresenter provideUsernamePresenter() {
        return mock(UsernamePresenter.class);
    }
}
