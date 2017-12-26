package com.orangepenguin.boilerplate.di;

import com.orangepenguin.boilerplate.rx.RxSchedulers;
import com.orangepenguin.boilerplate.screens.userdetails.UserDetailsPresenter;
import com.orangepenguin.boilerplate.screens.username.UsernamePresenter;
import com.orangepenguin.boilerplate.usecase.UserUseCase;
import com.orangepenguin.boilerplate.util.NotificationUtil;
import com.orangepenguin.boilerplate.util.SharedPreferencesUtil;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    @PerScreen
    public UsernamePresenter provideUsernamePresenter(UserUseCase userUseCase, SharedPreferencesUtil
            sharedPreferencesUtil, NotificationUtil notificationUtil, RxSchedulers schedulers) {
        return new UsernamePresenter(userUseCase, sharedPreferencesUtil, notificationUtil, schedulers);
    }

    @Provides
    @PerScreen
    public UserDetailsPresenter provideUserDetailsPresenter() {
        return new UserDetailsPresenter();
    }
}
