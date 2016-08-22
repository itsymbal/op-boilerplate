package com.orangepenguin.boilerplate.mvp.username;

import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.mvp.BasePresenter;
import com.orangepenguin.boilerplate.singletons.Constants;

public class UsernamePresenter extends BasePresenter implements UsernameContract.Presenter {

    private UsernameContract.View view;

    UsernamePresenter() {
        Injector.getPresenterComponent().inject(this);
    }

    @Override
    public void setView(UsernameContract.View view) {
        this.view = view;
        String savedUsername = view.getPreference(Constants.PREF_USERNAME, null);
        if (savedUsername != null) {
            view.checkRememberCheckbox();
            view.setUsername(savedUsername);
        }
    }

    @Override
    public void showUserButtonPressed(String username, boolean rememberChecked) {
        view.showMessage("showUserButtonPressed called with %s, %s", username, rememberChecked);

        // example of preference setting
        if (rememberChecked) {
            view.savePreference(Constants.PREF_USERNAME, username);
        } else {
            view.clearPreference(Constants.PREF_USERNAME);
        }

        view.startDetailsActivity(username);
    }
}