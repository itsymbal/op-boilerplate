package com.orangepenguin.boilerplate.screens.userdetails;

import android.os.Parcelable;

import com.orangepenguin.boilerplate.BasePresenter;
import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.model.User;

/**
 * This example presenter requests data for passed in username, keeps state of the request, and sets the state on
 * the view that is set at the time of event
 */
public class UserDetailsPresenter extends BasePresenter implements UserDetailsContract.Presenter {

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
        view.setUsername(user.login());
        if (user.name() != null) {
            view.setName(user.name());
        }
        if (user.company() != null) {
            view.setCompany(user.company());
        }
        if (user.avatarUrl() != null) {
            view.setAvatarUrl(user.avatarUrl());
        }
    }

    @Override
    public Parcelable onSaveState() {
        return user;
    }
}