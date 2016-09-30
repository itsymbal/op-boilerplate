package com.orangepenguin.boilerplate.di;

import com.orangepenguin.boilerplate.BasePresenterInterface;
import com.orangepenguin.boilerplate.screens.userdetails.UserDetailsContract;
import com.orangepenguin.boilerplate.screens.userdetails.UserDetailsPresenter;
import com.orangepenguin.boilerplate.screens.username.UsernameContract;
import com.orangepenguin.boilerplate.screens.username.UsernamePresenter;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class Injector {

    private static ApplicationComponent applicationComponent;
    private static PresenterComponent presenterComponent;
    private static ActivityComponent activityComponent;
    private static RepositoryComponent repositoryComponent;
    private static Map<Class, BasePresenterInterface> presenterMap = new HashMap<>();
    private static Map<Class, Class> presenterImplementations = new HashMap<>();

    // There may be a way to find an implementation using some Java reflection library, but this is both faster and
    // does not require another library import. The downside is you have to define and populate this map. The upside
    // is that the implementing class is configured here, external to the View, which only knows about Presenter
    // interface.
    static {
        presenterImplementations.put(UsernameContract.Presenter.class, UsernamePresenter.class);
        presenterImplementations.put(UserDetailsContract.Presenter.class, UserDetailsPresenter.class);
    }

    private Injector() {
        // private no-arg constructor for singleton class to force static usage
    }

    public static ActivityComponent getActivityComponent() {
        if (activityComponent != null) {
            return activityComponent;
        }
        return Injector.getApplicationComponent().plus(new ActivityModule());
    }

    public static void setActivityComponent(ActivityComponent activityComponent) {
        Injector.activityComponent = activityComponent;
    }

    public static PresenterComponent getPresenterComponent() {
        // presenter component is effectively a singleton. Once created it is never recreated
        if (presenterComponent == null) {
            presenterComponent = getApplicationComponent().plus(new PresenterModule());
        }
        return presenterComponent;
    }

    // setters allow setting a mock component from test code
    public static void setPresenterComponent(PresenterComponent presenterComponent) {
        Injector.presenterComponent = presenterComponent;
    }

    public static RepositoryComponent getRepositoryComponent() {
        // repository component is effectively a singleton. Once created it is never recreated
        if (repositoryComponent == null) {
            repositoryComponent = getApplicationComponent().plus(new RepositoryModule());
        }
        return repositoryComponent;
    }

    // setters allow setting a mock component from test code
    public static void setRepositoryComponent(RepositoryComponent repositoryComponent) {
        Injector.repositoryComponent = repositoryComponent;
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static void setApplicationComponent(ApplicationComponent applicationComponent) {
        Injector.applicationComponent = applicationComponent;
    }

    public static BasePresenterInterface getPresenter(Class presenterInterface) {
        if (presenterMap.containsKey(presenterInterface)) {
            return presenterMap.get(presenterInterface);
        }

        BasePresenterInterface presenter = null;
        try {
            Class presenterImplementationClass = presenterImplementations.get(presenterInterface);
            presenter = (BasePresenterInterface) presenterImplementationClass.newInstance();
        } catch (NullPointerException | InstantiationException | IllegalAccessException ex) {
            Timber.e(ex, "Error instantiating Presenter class for interface %s", presenterInterface.getCanonicalName());
        }
        return presenter;
    }

    public static void setPresenter(Class clazz, BasePresenterInterface presenter) {
        presenterMap.put(clazz, presenter);
    }
}