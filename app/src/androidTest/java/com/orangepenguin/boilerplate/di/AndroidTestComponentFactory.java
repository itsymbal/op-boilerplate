package com.orangepenguin.boilerplate.di;

import android.support.annotation.NonNull;

import com.orangepenguin.boilerplate.Application;

public final class AndroidTestComponentFactory implements ComponentFactory {

    public final AndroidTestApplicationComponent testApplicationComponent;

    public AndroidTestComponentFactory(@NonNull Application application) {
        testApplicationComponent = DaggerAndroidTestApplicationComponent
                .builder()
                .androidTestApplicationModule(new AndroidTestApplicationModule(application))
                .productionAndAndroidTestModule(new ProductionAndAndroidTestModule())
                .testAndAndroidTestModule(new TestAndAndroidTestModule())
                .build();
    }

    @Override
    @NonNull
    public ViewComponent getViewComponent() {
        return testApplicationComponent.plus(new PresenterModule());
    }
}
