package com.orangepenguin.boilerplate.mvp.username;

import com.orangepenguin.boilerplate.BasePresenterInterface;
import com.orangepenguin.boilerplate.BaseViewInterface;

interface UsernameContract {

    interface View extends BaseViewInterface {
        void startDetailsActivity(String username);
        void setUsername(String s);
        void checkRememberCheckbox();
    }

    interface Presenter extends BasePresenterInterface {
        void showUserButtonPressed(String username, boolean rememberChecked);
    }
}
