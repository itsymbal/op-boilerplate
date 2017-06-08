package com.orangepenguin.boilerplate.screens.userdetails;

import android.os.Parcelable;

import com.orangepenguin.boilerplate.BasePresenter;
import com.orangepenguin.boilerplate.model.User;

import javax.inject.Inject;

/**
 * This example presenter requests data for passed in username, keeps state of the request, and sets the state on
 * the view that is set at the time of event
 */
public class UserDetailsPresenter extends BasePresenter<UserDetailsView> {

    private User user;

    @Inject
    public UserDetailsPresenter() {
    }

    /**
     * set View variable and bring its state up to the current state of the Presenter
     */
    public void setUser(User user) {
        this.user = user;
        view.setUsername(this.user.login());
        if (this.user.name() != null) {
            view.setName(this.user.name());
        }
        if (this.user.company() != null) {
            view.setCompany(this.user.company());
        }
        if (this.user.avatarUrl() != null) {
            view.setAvatarUrl(this.user.avatarUrl());
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        return user;
    }
}
