package com.orangepenguin.boilerplate.mvp.userdetails;

import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.mvp.BasePresenter;

public class UserDetailsPresenter extends BasePresenter implements UserDetailsContract.Presenter {

    private UserDetailsContract.View view;
    final int STATE_INIT = 0;
    final int STATE_REQUEST_SENT = 1;
    final int STATE_RESPONSE_RECEIVED = 2;
    int state = STATE_INIT;

    UserDetailsPresenter() {
        Injector.getPresenterComponent().inject(this);
    }

    /**
     * set View variable and bring its state up to the current state of the Presenter
     * @param view
     */
    public void setView(UserDetailsContract.View view) {
        this.view = view;
        updateViewState();
    }

    private void updateViewState() {
        switch (state) {
            case STATE_INIT:
            case STATE_REQUEST_SENT:
                view.showLoadingIndicator();
                break;
            case STATE_RESPONSE_RECEIVED:
                view.hideLoadingIndicator();
//                view.setData(data);// figure out what the data looks like and set it on view
                break;
            default:
                // do nothing
        }
    }

    @Override
    public void onCreate(String username) {

        // this be where we use the username to go get user details
    }


}