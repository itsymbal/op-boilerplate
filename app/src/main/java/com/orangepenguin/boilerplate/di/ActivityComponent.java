package com.orangepenguin.boilerplate.di;

import com.orangepenguin.boilerplate.screens.userdetails.UserDetailsActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(UserDetailsActivity activity);
}
