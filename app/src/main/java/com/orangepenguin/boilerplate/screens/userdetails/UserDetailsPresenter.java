package com.orangepenguin.boilerplate.screens.userdetails;

import com.orangepenguin.boilerplate.BasePresenter;
import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.model.User;
import com.orangepenguin.boilerplate.rest.GitHubClient;

import javax.inject.Inject;

/**
 * This example presenter requests data for passed in username, keeps state of the request, and sets the state on
 * the view that is set at the time of event
 */
public class UserDetailsPresenter extends BasePresenter implements UserDetailsContract.Presenter {

    @Inject GitHubClient gitHubClient;
    private UserDetailsContract.View view;
    private User user;

    UserDetailsPresenter() {
        Injector.getPresenterComponent().inject(this);
    }

    /**
     * set View variable and bring its state up to the current state of the Presenter
     */
    public void setView(UserDetailsContract.View view, User user) {
        this.view = view;
        this.user = user;
        updateViewState();
    }

    private void updateViewState() {
        setUserDetailsOnView();
    }

    private void setUserDetailsOnView() {
        view.setUsername(user.getLogin());
        if (user.getName() != null) {
            view.setName(user.getName());
        }
        if (user.getCompany() != null) {
            view.setCompany(user.getCompany());
        }
        if (user.getAvatarUrl() != null) {
            view.setAvatarUrl(user.getAvatarUrl());
        }
    }

    // request data from GitHub backend

    /**
     * when requesting data from backend using Rx, make sure to capture returned Subscription object in the superclass'
     * subscription field. This way it will get unsubscribed in onDestroy()
     */
    private void requestData() {

    }
}