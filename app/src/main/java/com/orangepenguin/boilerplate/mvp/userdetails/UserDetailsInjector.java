package com.orangepenguin.boilerplate.mvp.userdetails;

import com.orangepenguin.boilerplate.di.Injector;

class UserDetailsInjector {

    private static UserDetailsComponent userDetailsComponent;

    private UserDetailsInjector() {
        // private no-arg constructor for singleton class to force static usage
    }

    static UserDetailsComponent getUserDetailsComponent() {
        if (userDetailsComponent != null) {
            return userDetailsComponent;
        }
        return Injector.getApplicationComponent().plus(new UserDetailsModule());
    }

    public static void setUserDetailsComponent(UserDetailsComponent userDetailsComponent) {
        UserDetailsInjector.userDetailsComponent = userDetailsComponent;
    }
}