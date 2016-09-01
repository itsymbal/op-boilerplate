package com.orangepenguin.boilerplate.screens.username;

import com.orangepenguin.boilerplate.BasePresenter;
import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.model.User;
import com.orangepenguin.boilerplate.rest.GitHubClient;
import com.orangepenguin.boilerplate.singletons.Constants;

import javax.inject.Inject;

import rx.Scheduler;
import timber.log.Timber;

import static com.orangepenguin.boilerplate.BasePresenter.PresenterState.REQUEST_IN_PROCESS;
import static com.orangepenguin.boilerplate.BasePresenter.PresenterState.REQUEST_NOT_IN_PROCESS;

public class UsernamePresenter extends BasePresenter implements UsernameContract.Presenter {

    @Inject GitHubClient gitHubClient;
    @Inject Scheduler observeScheduler;
    PresenterState presenterState = REQUEST_NOT_IN_PROCESS;
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
        subscription = gitHubClient.user(username)
                //TODO: check the issue described here
                //http://blog.bradcampbell.nz/keep-your-main-thread-synchronous/
                // it's likely not an issue, since *some* view will exist
                .observeOn(observeScheduler)
                .subscribe(
                user -> {
                    Timber.v("calling onNext() on thread" + Thread.currentThread().getName());
                    presenterState = REQUEST_NOT_IN_PROCESS;
                    view.hideLoadingIndicator();
                    view.startDetailsActivity(user);
                },
                throwable -> {
                    Timber.v("calling onError() on thread" + Thread.currentThread().getName());

                    presenterState = REQUEST_NOT_IN_PROCESS;
                    view.hideLoadingIndicator();
                    Timber.d(throwable, "error fetching user");
                    view.setUsernameError("No such user");
                }
        );
    }
}