package com.orangepenguin.boilerplate.di;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, AndroidTestOnlyModule.class})
public interface AndroidTestApplicationComponent extends ApplicationComponent {}
