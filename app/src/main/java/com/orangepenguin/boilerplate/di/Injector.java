package com.orangepenguin.boilerplate.di;

public class Injector {

    private static ApplicationComponent applicationComponent;
    private static PresenterComponent presenterComponent;

    private Injector() {
        // private no-arg constructor for singleton class to force static usage
    }

    public static PresenterComponent getPresenterComponent() {
        // presenter component is effectively a singleton. Once created it is never recreated
        if (presenterComponent == null) {
            presenterComponent = getApplicationComponent().plus(new PresenterModule());
        }
        return presenterComponent;
    }

    // setters allow setting a mock component from test code
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