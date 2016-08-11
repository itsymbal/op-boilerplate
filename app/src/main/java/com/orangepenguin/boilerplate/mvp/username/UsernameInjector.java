package com.orangepenguin.boilerplate.mvp.username;

import com.orangepenguin.boilerplate.di.Injector;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

public class UsernameInjector {

    private static UsernameComponent usernameComponent;

    private UsernameInjector() {
        // private no-arg constructor for singleton class to force static usage
    }

    static UsernameComponent getUsernameComponent() {
        if (usernameComponent != null) {
            return usernameComponent;
        }
        return Injector.getApplicationComponent().plus(new UsernameModule());
    }

    static void setUsernameComponent(UsernameComponent usernameComponent) {
        UsernameInjector.usernameComponent = usernameComponent;
    }

    @UsernameScope
    @Subcomponent(modules = {UsernameModule.class})
    public interface UsernameComponent {
        void inject(UsernameActivity activity);
    }

    @Module
    public static final class UsernameModule {
        @Provides
        UsernameContract.Presenter provideUsernamePresenter() {
            return new UsernamePresenter();
        }
    }
}