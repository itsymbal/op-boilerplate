package com.orangepenguin.boilerplate.di;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestRepositoryModule.class})
public interface TestRepositoryComponent extends RepositoryComponent {
}
