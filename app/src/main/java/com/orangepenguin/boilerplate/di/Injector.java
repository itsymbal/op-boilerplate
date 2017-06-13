package com.orangepenguin.boilerplate.di;

import android.support.annotation.NonNull;

public final class Injector {
    private static ComponentFactory componentFactory;

    private Injector() { }

    public static ComponentFactory getComponentFactory() {
        return componentFactory;
    }

    public static void setComponentFactory(@NonNull ComponentFactory componentFactory) {
        Injector.componentFactory = componentFactory;
    }
}
