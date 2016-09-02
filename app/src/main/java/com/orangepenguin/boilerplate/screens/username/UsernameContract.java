package com.orangepenguin.boilerplate.screens.username;

import com.orangepenguin.boilerplate.BasePresenterInterface;
import com.orangepenguin.boilerplate.BaseViewInterface;
import com.orangepenguin.boilerplate.model.User;

public interface UsernameContract {

    interface View extends BaseViewInterface {
        void startDetailsActivity(User user);
        void setUsername(String s);
        void checkRememberCheckbox();
        void setUsernameError(String error);
    }

    interface Presenter extends BasePresenterInterface {
        void setView(View view);

        void showUserButtonPressed(String username, boolean rememberChecked);
    }
}
