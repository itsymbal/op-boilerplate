package com.orangepenguin.boilerplate.di;

public class ComponentUtil {
    public static void setUpPresenterDependencies(TestPresenterModule testPresenterModule) {
        TestPresenterComponent testPresenterComponent =
                DaggerTestPresenterComponent
                        .builder()
                        .testPresenterModule(testPresenterModule)
                        .build();
        Injector.setPresenterComponent(testPresenterComponent);
    }
}
