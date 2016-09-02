package com.orangepenguin.boilerplate.di;

import com.orangepenguin.boilerplate.BasePresenter;
import com.orangepenguin.boilerplate.BasePresenterInterface;

import java.util.HashMap;
import java.util.Map;

public class Injector {

    private static ApplicationComponent applicationComponent;
    private static PresenterComponent presenterComponent;
    private static ActivityComponent activityComponent;
    private static Map<Class, BasePresenterInterface> presenterMap = new HashMap<>();

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

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static void setApplicationComponent(ApplicationComponent applicationComponent) {
        Injector.applicationComponent = applicationComponent;
    }

    public static BasePresenterInterface getPresenter(Class presenterClass) {
        if (presenterMap.containsKey(presenterClass)) {
            return presenterMap.get(presenterClass);
        }

        BasePresenter presenter = null;
        try {
            presenter = (BasePresenter) presenterClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            // TODO: figure out what to do with dis exceptions
        }
        return presenter;
    }

    public static void setPresenter(Class clazz, BasePresenterInterface presenter) {
        presenterMap.put(clazz, presenter);
    }
}