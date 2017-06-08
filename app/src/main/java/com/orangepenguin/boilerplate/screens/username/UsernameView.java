package com.orangepenguin.boilerplate.screens.username;

import com.orangepenguin.boilerplate.BaseViewInterface;
import com.orangepenguin.boilerplate.model.User;

public interface UsernameView extends BaseViewInterface {
    void startDetailsActivity(User user);
    void setUsername(String s);
    void checkRememberCheckbox();
    void setUsernameError(String error);
}
