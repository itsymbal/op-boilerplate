package com.orangepenguin.boilerplate.di;

import com.orangepenguin.boilerplate.BasePresenter;
import com.orangepenguin.boilerplate.screens.userdetails.UserDetailsPresenter;
import com.orangepenguin.boilerplate.screens.username.UsernamePresenter;

import javax.inject.Singleton;

import dagger.Subcomponent;

@Singleton
@Subcomponent(modules = {PresenterModule.class})
public interface PresenterComponent {
    void inject(BasePresenter basePresenter);
    void inject(UsernamePresenter usernamePresenter);
    void inject(UserDetailsPresenter userDetailsPresenter);
}
