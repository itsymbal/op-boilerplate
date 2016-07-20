package com.orangepenguin.boilerplate.mvp.username;

import dagger.Module;
import dagger.Provides;

@Module
public final class UsernameModule {
    @Provides
    UsernameContract.Presenter provideUsernamePresenter() {
        return new UsernamePresenter();
    }
}