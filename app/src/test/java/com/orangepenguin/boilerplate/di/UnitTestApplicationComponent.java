package com.orangepenguin.boilerplate.di;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestApplicationModule.class, TestAndAndroidTestModule.class})
public interface UnitTestApplicationComponent extends ApplicationComponent {
    TestViewComponent plus(TestPresenterModule presenterModule);
}
