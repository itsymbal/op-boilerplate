package com.orangepenguin.boilerplate.di;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AndroidTestApplicationModule.class,
        ProductionAndAndroidTestModule.class,
        TestAndAndroidTestModule.class})
public interface AndroidTestApplicationComponent extends ApplicationComponent {
}
