package com.orangepenguin.boilerplate.screens.userdetails;

import com.orangepenguin.boilerplate.di.Injector;

import dagger.Module;
import dagger.Subcomponent;

public class UserDetailsInjector {

    private static UserDetailsComponent userDetailsComponent;
    private static UserDetailsContract.Presenter presenter;

    private UserDetailsInjector() {
        // private no-arg constructor for singleton class to force static usage
    }

    static UserDetailsComponent getUserDetailsComponent() {
        if (userDetailsComponent != null) {
            return userDetailsComponent;
        }
        return Injector.getApplicationComponent().plus(new UserDetailsModule());
    }

    static void setUserDetailsComponent(UserDetailsComponent userDetailsComponent) {
        UserDetailsInjector.userDetailsComponent = userDetailsComponent;
    }

    public static UserDetailsContract.Presenter getPresenter() {
        if (presenter != null) {
            return presenter;
        }
        return new UserDetailsPresenter();
    }

    public static void setPresenter(UserDetailsContract.Presenter presenter) {
        UserDetailsInjector.presenter = presenter;
    }

    @Module
    public static final class UserDetailsModule {
    }

    @Subcomponent(modules = {UserDetailsModule.class})
    public interface UserDetailsComponent {
        void inject(UserDetailsActivity activity);
    }
}