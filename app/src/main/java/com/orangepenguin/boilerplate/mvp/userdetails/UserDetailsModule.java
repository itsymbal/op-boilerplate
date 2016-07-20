package com.orangepenguin.boilerplate.mvp.userdetails;

import dagger.Module;
import dagger.Provides;

@Module
public final class UserDetailsModule {
    @Provides
    UserDetailsContract.Presenter provideUserDetailsPresenter() {
        return new UserDetailsPresenter();
    }
}