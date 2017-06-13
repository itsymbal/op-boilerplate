package com.orangepenguin.boilerplate.di;

public final class UnitTestComponentFactory {

    private final UnitTestApplicationComponent applicationComponent;

    public UnitTestComponentFactory() {
        applicationComponent = DaggerUnitTestApplicationComponent
                .builder()
                .testApplicationModule(new TestApplicationModule())
                .build();
    }

    public UnitTestApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
