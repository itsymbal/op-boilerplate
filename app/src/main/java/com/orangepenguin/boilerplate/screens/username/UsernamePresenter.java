package com.orangepenguin.boilerplate.screens.username;

import com.orangepenguin.boilerplate.BasePresenter;
import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.singletons.Constants;

public class UsernamePresenter extends BasePresenter implements UsernameContract.Presenter {

    private UsernameContract.View view;

    UsernamePresenter() {
        Injector.getPresenterComponent().inject(this);
    }

    @Override
    public void setView(UsernameContract.View view) {
        this.view = view;
        String savedUsername = application.getPreference(Constants.PREF_USERNAME, null);
        if (savedUsername != null) {
            view.checkRememberCheckbox();
            view.setUsername(savedUsername);
        }
    }

    @Override
    public void showUserButtonPressed(String username, boolean rememberChecked) {
        showMessage("showUserButtonPressed called with %s, %s", username, rememberChecked);

        // example of preference setting
        if (rememberChecked) {
            application.savePreference(Constants.PREF_USERNAME, username);
        } else {
            application.clearPreference(Constants.PREF_USERNAME);
        }

        view.startDetailsActivity(username);
    }
}