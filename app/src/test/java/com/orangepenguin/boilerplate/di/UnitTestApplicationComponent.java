package com.orangepenguin.boilerplate.di;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestApplicationModule.class, TestAndAndroidTestModule.class})
public interface UnitTestApplicationComponent {
    TestViewComponent plus(TestPresenterModule presenterModule);
}
