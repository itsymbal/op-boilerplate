package com.orangepenguin.boilerplate.screens.userdetails;

import com.orangepenguin.boilerplate.BasePresenter;
import com.orangepenguin.boilerplate.di.Injector;

import static com.orangepenguin.boilerplate.BasePresenter.PresenterState.REQUEST_NOT_SENT;


public class UserDetailsPresenter extends BasePresenter implements UserDetailsContract.Presenter {

    PresenterState presenterState = REQUEST_NOT_SENT;
    private UserDetailsContract.View view;
    private String username;

    UserDetailsPresenter() {
        Injector.getPresenterComponent().inject(this);
    }

    /**
     * set View variable and bring its state up to the current state of the Presenter
     */
    public void setView(UserDetailsContract.View view, String username) {
        this.view = view;
        updateViewState();

        this.username = username;

        // this be where we use the username to go get user details
    }

    private void updateViewState() {
        switch (presenterState) {
            case REQUEST_NOT_SENT:
                requestData();
                break;
            case REQUEST_IN_PROCESS:
                view.showLoadingIndicator();
                break;
            case RESPONSE_RECEIVED:
                view.hideLoadingIndicator();
                //                view.setData(data);// figure out what the data looks like and set it on view
                break;
        }
    }

    // request data from GitHub backend
    private void requestData() {

    }
}