package com.orangepenguin.boilerplate.di;

import android.support.annotation.NonNull;

import com.orangepenguin.boilerplate.Application;

public final class AndroidTestComponentFactory implements ComponentFactory {

    private final AndroidTestApplicationComponent testApplicationComponent;

    public AndroidTestComponentFactory(@NonNull Application application) {
        testApplicationComponent = DaggerAndroidTestApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(application))
                .androidTestOnlyModule(new AndroidTestOnlyModule())
                .build();
    }

    @Override
    @NonNull
    public ViewComponent getViewComponent() {
        return testApplicationComponent.plus(new PresenterModule());
    }

    @NonNull
    @Override
    public ApplicationComponent getApplicationComponent() {
        return testApplicationComponent;
    }
}
