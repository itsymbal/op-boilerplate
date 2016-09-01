package com.orangepenguin.boilerplate.screens.username;

public class UsernameInjector {

    private static UsernameContract.Presenter presenter;

    private UsernameInjector() {
        // private no-arg constructor for singleton class to force static usage
    }

    public static UsernameContract.Presenter getPresenter() {
        if (presenter != null) {
            return presenter;
        }
        return new UsernamePresenter();
    }

    public static void setPresenter(UsernameContract.Presenter presenter) {
        UsernameInjector.presenter = presenter;
    }
}