package com.orangepenguin.boilerplate.di;

import android.support.annotation.NonNull;

import com.orangepenguin.boilerplate.Application;

public final class DefaultComponentFactory implements ComponentFactory {
    private final ApplicationComponent applicationComponent;

    public DefaultComponentFactory(@NonNull Application application) {
        applicationComponent =
                DaggerApplicationComponent
                        .builder()
                        .applicationModule(new ApplicationModule(application))
                        .productionOnlyModule(new ProductionOnlyModule())
                        .build();
    }

    @Override
    @NonNull
    public ViewComponent getViewComponent() {
        return applicationComponent.plus(new PresenterModule());
    }

    @NonNull
    @Override
    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
