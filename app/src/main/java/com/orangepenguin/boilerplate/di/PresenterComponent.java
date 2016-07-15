package com.orangepenguin.boilerplate.di;

import com.orangepenguin.boilerplate.mvp.username.UsernamePresenter;

import dagger.Subcomponent;

@PresenterScope
@Subcomponent(modules = {PresenterModule.class})
public interface PresenterComponent {
    void inject(UsernamePresenter usernamePresenter);

}
