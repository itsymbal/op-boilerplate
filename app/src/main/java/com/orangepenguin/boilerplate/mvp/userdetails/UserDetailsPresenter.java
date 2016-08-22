package com.orangepenguin.boilerplate.mvp.userdetails;

import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.mvp.BasePresenter;

import static com.orangepenguin.boilerplate.mvp.BasePresenter.PresenterState.REQUEST_NOT_IN_PROCESS;

public class UserDetailsPresenter extends BasePresenter implements UserDetailsContract.Presenter {

    PresenterState presenterState = REQUEST_NOT_IN_PROCESS;
    private UserDetailsContract.View view;
    private String username;

    UserDetailsPresenter() {
        Injector.getPresenterComponent().inject(this);
    }

    /**
     * set View variable and bring its state up to the current state of the Presenter
     */
    public void setView(UserDetailsContract.View view) {
        this.view = view;
        updateViewState();
    }

    @Override
    public void onCreate(String username) {

        this.username = username;

        // this be where we use the username to go get user details
    }

    private void updateViewState() {
        //        switch (state) {
        //            case STATE_INIT:
        //            case STATE_REQUEST_SENT:
        //                view.showLoadingIndicator();
        //                break;
        //            case STATE_RESPONSE_RECEIVED:
        //                view.hideLoadingIndicator();
        ////                view.setData(data);// figure out what the data looks like and set it on view
        //                break;
        //            default:
        //                // do nothing
        //        }
    }
}