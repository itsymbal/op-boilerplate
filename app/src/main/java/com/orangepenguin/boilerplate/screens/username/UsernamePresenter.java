package com.orangepenguin.boilerplate.screens.username;

import com.orangepenguin.boilerplate.BasePresenter;
import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.model.User;
import com.orangepenguin.boilerplate.rest.GitHubClient;
import com.orangepenguin.boilerplate.singletons.Constants;

import javax.inject.Inject;

import timber.log.Timber;

public class UsernamePresenter extends BasePresenter implements UsernameContract.Presenter {

    @Inject GitHubClient gitHubClient;
    private UsernameContract.View view;
    private User user;

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

        fetchUser(username);
    }

    private User fetchUser(String username) {

        view.showLoadingIndicator();
        subscription = gitHubClient.user(username).subscribe(
                user -> {
                    view.startDetailsActivity(user);
                },
                throwable -> {
                    view.hideLoadingIndicator();
                    Timber.d(throwable, "error fetching user");
                    view.setUsernameError("No such user");
                }
        );

        return null;
    }
}