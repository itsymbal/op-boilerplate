package com.orangepenguin.boilerplate.mvp.username;

import com.orangepenguin.boilerplate.di.Injector;

public class UsernameInjector {

    private static UsernameComponent usernameComponent;

    private UsernameInjector() {
        // private no-arg constructor for singleton class to force static usage
    }

    static UsernameComponent getUsernameComponent() {
        if (usernameComponent != null) {
            return usernameComponent;
        }
        return Injector.getApplicationComponent().plus(new UsernameModule());
    }

    static void setUsernameComponent(UsernameComponent usernameComponent) {
        UsernameInjector.usernameComponent = usernameComponent;
    }
}