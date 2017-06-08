package com.orangepenguin.boilerplate.di;

import android.support.annotation.NonNull;

public final class UnitTestComponentFactory implements ComponentFactory {

    private final UnitTestApplicationComponent applicationComponent;
    private TestViewComponent testViewComponent;

    public UnitTestComponentFactory() {
        applicationComponent = DaggerUnitTestApplicationComponent
                .builder()
                .testApplicationModule(new TestApplicationModule())
                .testAndAndroidTestModule(new TestAndAndroidTestModule())
                .build();
    }

    @Override
    @NonNull
    public TestViewComponent getViewComponent() {
        if (testViewComponent == null) {
            testViewComponent = applicationComponent.plus(new TestPresenterModule());
        }
        return testViewComponent;
    }
}
