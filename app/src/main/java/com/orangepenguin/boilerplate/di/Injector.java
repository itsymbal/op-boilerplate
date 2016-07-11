package com.orangepenguin.boilerplate.di;

import com.orangepenguin.boilerplate.mvp.username.UsernameComponent;
import com.orangepenguin.boilerplate.mvp.username.UsernameModule;

public class Injector {

    private static ApplicationComponent applicationComponent;
    private static PresenterComponent presenterComponent;
    private static UsernameComponent usernameComponent;

    private Injector() {
        // private no-arg constructor for singleton class to force static usage
    }

    public static PresenterComponent getPresenterComponent() {
        if (presenterComponent != null) {
            return presenterComponent;
        }
        return getApplicationComponent().plus(new PresenterModule());
    }

    public static void setPresenterComponent(PresenterComponent presenterComponent) {
        Injector.presenterComponent = presenterComponent;
    }

    public static UsernameComponent getUsernameComponent() {
        if (usernameComponent != null) {
            return usernameComponent;
        }
        return getApplicationComponent().plus(new UsernameModule());
    }

    public static void setUsernameComponent(UsernameComponent usernameComponent) {
        Injector.usernameComponent = usernameComponent;
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static void setApplicationComponent(ApplicationComponent applicationComponent) {
        Injector.applicationComponent = applicationComponent;
    }

}