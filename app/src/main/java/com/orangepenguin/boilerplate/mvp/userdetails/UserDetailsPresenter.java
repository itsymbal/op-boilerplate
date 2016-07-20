package com.orangepenguin.boilerplate.mvp.userdetails;

import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.mvp.BasePresenter;

public class UserDetailsPresenter extends BasePresenter implements UserDetailsContract.Presenter {

    private UserDetailsContract.View view;

    public void setView(UserDetailsContract.View view, String username) {
        this.view = view;
        Injector.getPresenterComponent().inject(this);
    }
}