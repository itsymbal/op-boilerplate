package com.orangepenguin.boilerplate.di;

import com.orangepenguin.boilerplate.BaseApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * This is a Dagger component. Refer to {@link BaseApplication} for the list of Dagger components
 * used in this application.
 * <p>
 * Even though Dagger allows annotating a {@link Component @Component} as a singleton, the code
 * itself must ensure only one instance of the class is created. This is done in {@link
 * BaseApplication}.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    ActivityComponent plus(ActivityModule userDetailsModule);
    PresenterComponent plus(PresenterModule presenterModule);
}
