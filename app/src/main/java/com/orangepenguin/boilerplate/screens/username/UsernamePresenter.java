package com.orangepenguin.boilerplate.screens.username;

import com.orangepenguin.boilerplate.BasePresenter;
import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.repository.UserRepository;
import com.orangepenguin.boilerplate.singletons.Constants;

import javax.inject.Inject;

import timber.log.Timber;

import static com.orangepenguin.boilerplate.BasePresenter.PresenterState.REQUEST_IN_PROCESS;
import static com.orangepenguin.boilerplate.BasePresenter.PresenterState.REQUEST_NOT_IN_PROCESS;

public class UsernamePresenter extends BasePresenter implements UsernameContract.Presenter {

    @Inject UserRepository userRepository;

    PresenterState presenterState = REQUEST_NOT_IN_PROCESS;
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
        setViewState();
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

        fetchUser(username.trim());
    }

    private void setViewState() {
        if (presenterState == REQUEST_IN_PROCESS) {
            view.showLoadingIndicator();
        }
    }

    private void fetchUser(String username) {
        view.showLoadingIndicator();
        presenterState = REQUEST_IN_PROCESS;
        subscription = userRepository
                .fetchUser(username)
                .subscribe(
                        user -> {
                            presenterState = REQUEST_NOT_IN_PROCESS;
                            view.hideLoadingIndicator();
                            view.startDetailsActivity(user);
                        },
                        throwable -> {
                            presenterState = REQUEST_NOT_IN_PROCESS;
                            view.hideLoadingIndicator();
                            Timber.d(throwable, "error fetching user");
                            // TODO: figure out type of error and do the right thing:
                            // 1 - no network, 2 - network error, 3 - bad username
                            view.setUsernameError("No such user");
                        }
                );
    }
}