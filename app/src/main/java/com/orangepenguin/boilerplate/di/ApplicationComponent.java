package com.orangepenguin.boilerplate.di;

import com.orangepenguin.boilerplate.Application;

import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

/**
 * This is a Dagger component. Refer to {@link Application} for the list of Dagger components
 * used in this application.
 * <p>
 * Even though Dagger allows annotating a {@link Component @Component} as a singleton, the code
 * itself must ensure only one instance of the class is created. This is done in {@link
 * Application}.
 */
@Singleton
@Component(modules = {ApplicationModule.class, ProductionOnlyModule.class})
public interface ApplicationComponent {
    ViewComponent plus(PresenterModule presenterModule);
    @Named("serverUrl")
    AtomicReference<String> getGitHubUrl();
}
