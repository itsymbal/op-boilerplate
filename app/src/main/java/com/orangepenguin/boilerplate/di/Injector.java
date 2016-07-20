package com.orangepenguin.boilerplate.di;

public class Injector {

    private static ApplicationComponent applicationComponent;
    private static PresenterComponent presenterComponent;

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

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static void setApplicationComponent(ApplicationComponent applicationComponent) {
        Injector.applicationComponent = applicationComponent;
    }
}