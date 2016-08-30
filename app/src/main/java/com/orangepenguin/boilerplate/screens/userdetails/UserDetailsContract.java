package com.orangepenguin.boilerplate.screens.userdetails;

import com.orangepenguin.boilerplate.BasePresenterInterface;
import com.orangepenguin.boilerplate.BaseViewInterface;
import com.orangepenguin.boilerplate.model.User;

interface UserDetailsContract {

    interface View extends BaseViewInterface {
        void setUsername(String usrename);
        void setCompany(String company);
        void setName(String name);
        void setAvatarUrl(String avatarUrl);
    }

    interface Presenter extends BasePresenterInterface {
        void setView(View view, User user);
    }
}
