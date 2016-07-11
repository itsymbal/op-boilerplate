package com.orangepenguin.boilerplate.mvp.username;

import dagger.Module;
import dagger.Provides;

@Module
public final class UsernameModule {

    @Provides
    UsernameContract.Presenter provideSimpleScreenPresenter() {
        return new UsernamePresenter();
    }
}